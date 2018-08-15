package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;

@Getter
@Setter
public class WorkChangeDetail {
	/**
	* 勤務変更申請
	*/
	AppWorkChange appWorkChange;		
	/**
	 * 申請
	 */
	Application_New application;	
	/**
	 * 申請者名
	 */
	private String employeeName;
	/**
	 * 申請者社員ID
	 */
	private String sID;
		
	DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput;

	PrelaunchAppSetting prelaunchAppSetting;

	DetailScreenInitModeOutput detailScreenInitModeOutput;	
	/**
	 * 選択可能な勤務種類コード
	 */
	List<String> workTypeCodes;
	/**
	 * 選択可能な就業時間帯コード
	 */
	List<String> workTimeCodes;
	
	boolean isTimeRequired;
}
