package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripPrintContent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
public class BusinessTripDto {

    private Integer departureTime;

    private Integer returnTime;

    private List<BusinessTripInfoDto> tripInfos;

    public BusinessTrip toDomain(Application app) {
        return new BusinessTrip(
                this.getTripInfos().stream().map(i -> i.toDomain()).collect(Collectors.toList()),
                this.getDepartureTime(),
                this.getReturnTime(),
                app
        );
    }

    public BusinessTripPrintContent toPrintContentOutput() {
        return new BusinessTripPrintContent(
                this.getTripInfos().stream().map(i -> i.toDomain()).collect(Collectors.toList()),
                this.getDepartureTime(),
                this.getReturnTime()
        );
    }

    public static BusinessTripDto fromDomain(BusinessTrip domain) {

        return new BusinessTripDto(
                domain.getDepartureTime().isPresent() ? domain.getDepartureTime().get().v() : null,
                domain.getReturnTime().isPresent() ? domain.getReturnTime().get().v() : null,
                domain.getInfos().stream().map(i -> BusinessTripInfoDto.fromDomain(i)).collect(Collectors.toList())
        );
    }

}
