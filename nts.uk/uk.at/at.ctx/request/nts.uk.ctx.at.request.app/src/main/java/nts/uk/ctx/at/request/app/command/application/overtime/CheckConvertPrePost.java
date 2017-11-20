package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckConvertPrePost {
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	public OverTimeDto convertPrePost(int prePostAtr,String appDate){
		
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		OverTimeDto result = new OverTimeDto();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		if(prePostAtr == 1){
			if(overtimeRestAppCommonSet.isPresent()){
				if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr() == UseAtr.USE){
					result.setReferencePanelFlg(true);
					//to do....
				}
				if(overtimeRestAppCommonSet.get().getPreDisplayAtr() == UseAtr.USE ){
					if(iOvertimePreProcess.getPreApplication(employeeID, overtimeRestAppCommonSet, appDate, prePostAtr) != null){
						result.setPreAppPanelFlg(true);
					}else{
						result.setPreAppPanelFlg(false);
					}
				}
			}
		}else if(prePostAtr ==0){
			if(overtimeRestAppCommonSet.isPresent()){
				if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr() == UseAtr.USE){
					result.setReferencePanelFlg(false);
					//to do....
				}
				if(overtimeRestAppCommonSet.get().getPreDisplayAtr() == UseAtr.USE ){
					result.setPreAppPanelFlg(false);
					//to do....
				}
			}
		}
		
		
		return result;
	}
	
	
}
