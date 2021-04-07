package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppCommonSettingOutput {
	
	public GeneralDate generalDate;
	
//	public ApplicationSetting applicationSetting;

	// public ApprovalFunctionSetting approvalFunctionSetting;
	
	// public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	// public List<ApplicationDeadline> applicationDeadlines;
	/**
	 * 雇用別申請承認設定
	 */
//	public Optional<AppEmploymentSetting> appEmploymentWorkType;

	public AppCommonSettingOutput() {
		super();
		this.generalDate = null;
//		this.applicationSetting = null;
		// this.approvalFunctionSetting = null;
		// this.appTypeDiscreteSettings = new ArrayList<>();
		// this.applicationDeadlines = new ArrayList<>();
	}
}
