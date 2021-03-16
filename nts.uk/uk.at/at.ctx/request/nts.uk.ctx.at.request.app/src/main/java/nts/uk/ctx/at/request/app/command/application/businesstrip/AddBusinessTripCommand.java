package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

@Data
public class AddBusinessTripCommand {

    // 出張申請
    private BusinessTripCommand businessTrip;

    // 出張申請の表示情報
    private BusinessTripInfoOutputCommand businessTripInfoOutput;

    // 申請
    private ApplicationDto application;

}
