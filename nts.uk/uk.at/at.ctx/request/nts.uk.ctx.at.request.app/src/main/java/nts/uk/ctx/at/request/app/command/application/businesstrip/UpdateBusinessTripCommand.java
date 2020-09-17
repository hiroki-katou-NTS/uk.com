package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusinessTripCommand {

    // 出張申請
    private BusinessTripCommand businessTrip;

    // 出張申請の表示情報
    private BusinessTripInfoOutputCommand businessTripInfoOutput;

    // 申請
    private CreateApplicationCommand application;

}
