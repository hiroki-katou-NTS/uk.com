package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Data
public class StartProcessTimeLeaveParams {

    // 申請ID
    private String appId;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartupOutput;

}
