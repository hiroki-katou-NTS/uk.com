package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
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

	public RequestAppDetailSetting requestOfEachCommon;
	
	public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	public List<ApplicationDeadline> applicationDeadlines;
}
