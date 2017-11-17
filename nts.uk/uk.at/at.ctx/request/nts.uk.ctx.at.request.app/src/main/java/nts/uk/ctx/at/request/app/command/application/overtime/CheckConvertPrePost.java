package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckConvertPrePost {
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	
	public OverTimeDto convertFromPreToPost(String appDate){
		
		String companyID = AppContexts.user().companyId();
		
		OverTimeDto result = new OverTimeDto();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr() == UseAtr.USE){
				result.setReferencePanelFlg(true);
				//to do....
			}
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr() == UseAtr.USE ){
				result.setPreAppPanelFlg(true);
				//to do....
			}
		}
		return result;
	}
	
	
}
