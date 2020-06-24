package nts.uk.ctx.at.request.dom.application.lateorleaveearly;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.ArrivedLateLeaveEralySetting;
@AllArgsConstructor
@NoArgsConstructor
@Data
//遅刻早退取消申請起動時の表示情報
public class ArrivedLateLeaveEarlyOutput {
//	取り消す初期情報
	private List<LateOrEarlyInfo> earlyInfos;
	// now, this is not completed so do not use it
//	申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
//	遅刻早退取消申請設定
	private ArrivedLateLeaveEralySetting arrivedLateLeaveEralySetting;
//	エアー情報
	private Character info;
//	遅刻早退取消申請
	private ArrivedLateLeaveEarly arrivedLateLeaveEarly;
	
}
