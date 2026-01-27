package interaction.api.feign.contract;

import interaction.api.dto.request.*;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface RequestContract {
    @PostMapping(
            value = "/requests",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ParticipationRequestDto createRequest(@RequestBody CreateRequestCommand command);

    @PatchMapping(
            value = "/requests/{requestId}/cancel",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ParticipationRequestDto cancelRequest(@PathVariable("requestId") @Positive Long requestId,
                                          @RequestBody CancelRequestCommand command);

    @GetMapping("/users/{userId}/requests/{eventId}")
    List<ParticipationRequestDto> getCurrentUserEventRequests(@PathVariable("userId") @Positive Long initiatorId,
                                                                     @PathVariable("eventId") @Positive Long eventId);

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/requests/status/bulk-update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    EventRequestStatusUpdateResultDto updateParticipationRequestsStatus(
            @RequestBody BulkRequestStatusUpdateCommand command);

    @GetMapping("/users/requests/confirmed")
    Map<Long, List<ParticipationRequestDto>> prepareConfirmedRequests(@RequestParam List<Long> eventIds);
}
