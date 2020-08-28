package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AddBusinessTripCommand {

    // 出張申請
    private BusinessTripDto businessTripDto;

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    // 申請
    private ApplicationDto applicationDto;

}
