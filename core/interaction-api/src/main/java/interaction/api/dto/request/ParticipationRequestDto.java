package interaction.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import interaction.api.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static interaction.api.utility.AppConstants.DATE_TIME_WITH_MILLIS_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_WITH_MILLIS_FORMAT)
    @NotNull(message = "Дата создания не может быть пустой")
    LocalDateTime created;

    @JsonProperty("event")
    @NotNull(message = "eventId не может быть пустым")
    Long eventId;

    @JsonProperty("requester")
    @NotNull(message = "requesterId не может быть пустым")
    Long requesterId;

    @NotNull(message = "Статус не может быть пустым")
    RequestStatus status;
}
