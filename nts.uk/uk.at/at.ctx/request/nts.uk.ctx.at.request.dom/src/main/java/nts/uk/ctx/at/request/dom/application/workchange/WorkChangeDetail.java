package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;

@Getter
@Setter
public class WorkChangeDetail {
	/**
	* 勤務変更申請
	*/
	AppWorkChange appWorkChange;	
	/**
	 * 事前事後区分
	 */
	int prePostAtr;
	/**
	 * 申請理由
	 */
	String appReason;
	/**
	 * 申請日
	 */
	GeneralDate appDate;
	/**
	 * 申請者名
	 */
	private String employeeName;
	/**
	 * 申請者社員ID
	 */
	private String sID;
	
	/**
	 * 定型理由のリストにセットするため
	 */
	private List<ApplicationReason> listAppReason;
	
	DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput;

	PrelaunchAppSetting prelaunchAppSetting;

	DetailScreenInitModeOutput detailScreenInitModeOutput;
}
