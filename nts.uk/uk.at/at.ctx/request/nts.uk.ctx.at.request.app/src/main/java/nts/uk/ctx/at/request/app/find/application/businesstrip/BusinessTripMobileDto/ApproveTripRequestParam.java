package nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;

import java.util.List;

@Data
public class ApproveTripRequestParam {

    // 画面モード
    private boolean isNewMode;

    // 申請種類
    private int appType;

    // 申請者リスト
    private List<String> employeeIds;

    // 申請対象日リスト
    private List<String> listDate;

    // 出張申請
    private BusinessTripDto businessTrip;

    // 出張申請の表示情報
    private BusinessTripInfoOutputDto businessTripInfoOutput;

}
