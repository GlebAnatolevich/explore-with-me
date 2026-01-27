package interaction.api.feign.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import interaction.api.exception.ConflictException;
import interaction.api.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeignErrorDecoder implements ErrorDecoder {
    ErrorDecoder defaultDecoder = new Default();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = getResponseBody(response);

        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus == HttpStatus.NOT_FOUND) {
            String message = extractMessageFromJson(responseBody);
            log.warn("Получен 404 Not Found от Feign. Тело ответа: {}", responseBody);
            return new NotFoundException(message);
        }

        if (responseStatus == HttpStatus.CONFLICT) {
            String message = extractMessageFromJson(responseBody);
            log.warn("Получен 409 Conflict от Feign. Тело ответа: {}", responseBody);
            return new ConflictException(message);
        }

        log.error("Необработанная ошибка Feign. Статус: {}, Тело: {}", response.status(), responseBody);
        return defaultDecoder.decode(methodKey, response);
    }

    private String getResponseBody(Response response) {
        if (response.body() == null) {
            return "Тело ответа отсутствует";
        }
        try (InputStream bodyIs = response.body().asInputStream()) {
            return new String(bodyIs.readAllBytes());
        } catch (IOException e) {
            log.error("Не удалось прочитать тело ответа Feign", e);
            return "Не удалось прочитать тело ответа";
        }
    }

    private String extractMessageFromJson(String json) {
        if (json == null || json.isEmpty()) {
            return "Сообщение об ошибке не предоставлено";
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode.has("message")) {
                return jsonNode.get("message").asText();
            }
        } catch (IOException e) {
            log.error("Не удалось распарсить JSON из тела ошибки Feign: {}", json, e);
            return json;
        }
        return json;
    }
}
