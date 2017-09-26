package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachCommon;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
public class AppCommonSettingOutput {
	
	public GeneralDate generalDate;
	
	public ApplicationSetting applicationSetting;  

	public RequestOfEachCommon requestOfEachCommon;
	
	public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	public List<ApplicationDeadline> applicationDeadlines;
}
