package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class BusinessTripCommand {

    // 出発時刻
    private Integer departureTime;

    // 帰着時刻
    private Integer returnTime;

    // 出張勤務情報
    private List<BusinessTripInfoDto> tripInfos;

    public BusinessTrip toDomain(Application app) {
        return new BusinessTrip(
                this.getTripInfos().stream().map(i -> i.toDomain()).collect(Collectors.toList()),
                this.getDepartureTime(),
                this.getReturnTime(),
                app
        );
    }

}
