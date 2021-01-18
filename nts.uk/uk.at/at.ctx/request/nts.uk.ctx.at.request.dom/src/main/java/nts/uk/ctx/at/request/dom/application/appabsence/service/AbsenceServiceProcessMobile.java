package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

public interface AbsenceServiceProcessMobile {
	
	public static final Integer MODE_NEW = 0;
	
	public static final Integer MODE_UDATE = 1;
	
	/**
	 * Refactor5 休暇申請入力画面を起動する
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.アルゴリズム.休暇申請入力画面を起動する
	 * @param mode
	 * @param companyId
	 * @param employeeIdOp
	 * @param datesOp
	 * @param appAbsenceStartInfoOutputOp
	 * @param applyForLeaveOp
	 * @return
	 */
	public AppForLeaveStartOutput start(
			Integer mode,
			String companyId,
			Optional<String> employeeIdOp,
			List<GeneralDate> datesOp,
			Optional<AppAbsenceStartInfoOutput> appAbsenceStartInfoOutputOp,
			Optional<ApplyForLeave> applyForLeaveOp,
			AppDispInfoStartupOutput appDispInfoStartupOutput
			);
	
	/**
	 * Refactor5 申請日を変更する
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.アルゴリズム.申請日を変更する
	 * @param companyId
	 * @param datesOp
	 * @param appAbsenceStartInfoOutputOp
	 * @param applyForLeaveOp
	 * @return
	 */
	public AppAbsenceStartInfoOutput getChangeDate(
			String companyId,
			List<GeneralDate> datesOp,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutputOp,
			Optional<ApplyForLeave> applyForLeaveOp
			);
}
