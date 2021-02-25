package nts.uk.ctx.at.request.dom.application.lateorleaveearly;


import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
@AllArgsConstructor
@NoArgsConstructor
@Data
//遅刻早退取消申請起動時の表示情報
public class ArrivedLateLeaveEarlyInfoOutput {
//	取り消す初期情報
	private List<LateOrEarlyInfo> earlyInfos;
	// now, this is not completed so do not use it
//	申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
//	遅刻早退取消申請設定
	private LateEarlyCancelAppSet lateEarlyCancelAppSet;
//	エアー情報
	private Optional<String> info = Optional.empty();
//	遅刻早退取消申請
	private Optional<ArrivedLateLeaveEarly> arrivedLateLeaveEarly = Optional.empty();;
	
}
