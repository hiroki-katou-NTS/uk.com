package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusinessTripCommand {

    // 出張申請
    private BusinessTripDto businessTripDto;

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    // 申請
    private ApplicationDto applicationDto;

}
