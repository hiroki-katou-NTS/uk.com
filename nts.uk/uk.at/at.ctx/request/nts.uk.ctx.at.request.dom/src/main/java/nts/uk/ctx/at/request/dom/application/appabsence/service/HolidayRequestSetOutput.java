package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HolidayRequestSetOutput {
	
	/**
	 * 休暇申請設定
	 */
	private HdAppSet hdAppSet;
	
//	/**
//	 * 申請理由表示
//	 */
//	private List<DisplayReason> displayReasonLst;
	
	/**
	 * 休暇申請の反映
	 */
	private VacationApplicationReflect vacationAppReflect;
}
