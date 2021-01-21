package nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Data
public class StartScreenBDto {

    // 申請ID
    private String appId;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartup;

}
