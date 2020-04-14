package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;

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
	
	/**
	 * 申請理由表示
	 */
	private List<DisplayReason> displayReasonLst;
}
