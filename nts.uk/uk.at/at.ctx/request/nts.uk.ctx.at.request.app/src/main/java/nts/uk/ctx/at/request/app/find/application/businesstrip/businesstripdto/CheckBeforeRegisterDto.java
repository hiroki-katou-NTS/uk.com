package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

@Data
public class CheckBeforeRegisterDto {

    // 出張申請
    private BusinessTripDto businessTripDto;

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    // 申請
    private ApplicationDto applicationDto;

}
