package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppCommonSettingOutput {
	
	public GeneralDate generalDate;
	
	public ApplicationSetting applicationSetting;  

	public ApprovalFunctionSetting approvalFunctionSetting;
	
	public List<AppTypeDiscreteSetting> appTypeDiscreteSettings;  
	
	public List<ApplicationDeadline> applicationDeadlines;
	/**
	 * 雇用別申請承認設定
	 */
	public List<AppEmploymentSetting> appEmploymentWorkType;

	public AppCommonSettingOutput() {
		super();
		this.generalDate = null;
		this.applicationSetting = null;
		this.approvalFunctionSetting = null;
		this.appTypeDiscreteSettings = new ArrayList<>();
		this.applicationDeadlines = new ArrayList<>();
	}
}
