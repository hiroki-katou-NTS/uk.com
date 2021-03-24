package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;

@Stateless
public class AbsenceServiceProcessMobileImpl implements AbsenceServiceProcessMobile {
	
	@Inject
	private AbsenceServiceProcess absenceServiceProcess;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Override
	public AppForLeaveStartOutput start(
			Integer mode,
			String companyId,
			Optional<String> employeeIdOp,
			List<GeneralDate> datesOp,
			Optional<AppAbsenceStartInfoOutput> appAbsenceStartInfoOutputOp,
			Optional<ApplyForLeave> applyForLeaveOp,
			AppDispInfoStartupOutput appDispInfoStartupOutput) {
		AppForLeaveStartOutput result = new AppForLeaveStartOutput(null, null);
		// INPUT．「画面モード」を確認する
		if (mode == AbsenceServiceProcessMobile.MODE_NEW) {
			// 起動時の申請表示情報を取得する from client
			// 1.休暇申請（新規）起動前処理
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenceServiceProcess.getVacationActivation(
					companyId,
					appDispInfoStartupOutput);
			result.setAppAbsenceStartInfoOutput(appAbsenceStartInfoOutput);
		} else {
			// INPUT「休暇申請起動時の表示情報」と「休暇申請」を返す
			result.setAppAbsenceStartInfoOutput(appAbsenceStartInfoOutputOp.orElse(null));
			result.setApplyForLeave(applyForLeaveOp.orElse(null));
		}
		return result;
	}

	@Override
	public AppAbsenceStartInfoOutput getChangeDate(String companyId, List<GeneralDate> datesOp,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutputOp, Optional<ApplyForLeave> applyForLeaveOp) {
		// 申請表示情報(基準日関係あり)を取得する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.getAppDispInfoWithDate(
				companyId,
				ApplicationType.OVER_TIME_APPLICATION,
				datesOp,
				appAbsenceStartInfoOutputOp.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput(),
				true,
				Optional.empty());
		appAbsenceStartInfoOutputOp.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		// 申請日変更時処理 
		// absenceServiceProcess.getDa
		return null;
	}

}
