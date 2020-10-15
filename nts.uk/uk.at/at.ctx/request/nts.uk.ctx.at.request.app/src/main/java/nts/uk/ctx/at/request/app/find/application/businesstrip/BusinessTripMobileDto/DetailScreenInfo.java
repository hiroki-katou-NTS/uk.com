package nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;

@Data
@NoArgsConstructor
public class DetailScreenInfo {

    // 出張申請
    private BusinessTripDto businessTrip;

    // 出張申請の表示情報
    private BusinessTripInfoOutputDto businessTripInfoOutput;

}
