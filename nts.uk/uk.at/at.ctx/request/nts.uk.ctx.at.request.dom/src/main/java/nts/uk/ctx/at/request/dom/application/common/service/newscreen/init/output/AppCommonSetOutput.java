package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class AppCommonSetOutput {
	
	public GeneralDate baseDate;
	
	public ApplicationSetting applicationSetting;  

	// public ApprovalFunctionSetting approvalFunctionSetting;
	
	// public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	// public List<ApplicationDeadline> applicationDeadlines;
}
