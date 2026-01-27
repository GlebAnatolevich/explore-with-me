package request.service.controller;

import interaction.api.dto.request.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import request.service.service.RequestService;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestController {
    RequestService requestService;

    @PatchMapping("/requests/status/bulk-update")
    public EventRequestStatusUpdateResultDto bulkUpdateStatus(
            @Valid @RequestBody BulkRequestStatusUpdateCommand command) {
        return requestService.updateParticipationRequestsStatus(
                command.getInitiatorId(),
                command.getEventId(),
                command.getUpdateDto()
        );
    }

    @PostMapping("/requests")
    public ParticipationRequestDto createRequest(@Valid @RequestBody CreateRequestCommand command) {
        return requestService.createRequest(command.getInitiatorId(), command.getEventId());
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long requestId,
                                                 @Valid @RequestBody CancelRequestCommand command) {
        return requestService.cancelRequest(command.getInitiatorId(), requestId);
    }
}
