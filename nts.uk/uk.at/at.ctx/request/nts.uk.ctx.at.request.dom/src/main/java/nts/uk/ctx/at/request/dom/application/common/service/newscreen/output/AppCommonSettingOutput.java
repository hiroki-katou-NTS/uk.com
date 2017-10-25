package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCommon;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public class AppCommonSettingOutput {
	
	public GeneralDate generalDate;
	
	public ApplicationSetting applicationSetting;  

	public RequestOfEachCommon requestOfEachCommon;
	
	public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	public List<ApplicationDeadline> applicationDeadlines;

	public AppCommonSettingOutput() {
		super();
		this.generalDate = null;
		this.applicationSetting = null;
		this.requestOfEachCommon = null;
		this.appTypeDiscreteSettings = new ArrayList<>();
		this.applicationDeadlines = new ArrayList<>();
	}
}
