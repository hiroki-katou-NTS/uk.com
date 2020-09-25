package nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;

@Data
public class CheckPeriodDto {

    // 起動時のモード
    private Boolean isNewMode;

    //	承認ルートエラー情報
    private int isError;

    // 申請
    private ApplicationDto application;

    // 出張申請
    private BusinessTripDto businessTrip;

    // 出張申請の表示情報
    private BusinessTripInfoOutputDto businessTripInfoOutput;

}
