package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoWithDateApplication;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

public class DisplayInfoOverTimeDto {
		// 基準日に関する情報
		public InfoBaseDateOutputDto infoBaseDateOutput;
		// 基準日に関係しない情報
		public InfoNoBaseDate infoNoBaseDate;
		// 休出枠
		public List<WorkdayoffFrame> workdayoffFrames;
		// 残業申請区分
		public OvertimeAppAtr overtimeAppAtr;
		// 申請表示情報
		public AppDispInfoStartupOutput appDispInfoStartup;
		// 代行申請か
		public Boolean isProxy;
		// 計算結果
		public Optional<CalculationResult> calculationResultOp = Optional.empty();
		// 申請日に関係する情報
		public Optional<InfoWithDateApplication> infoWithDateApplicationOp = Optional.empty();
}
