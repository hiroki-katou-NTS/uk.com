package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;

@Stateless
public class AppOvertimeSettingFinder {
	@Inject
	private AppOvertimeSettingRepository appOverRep;
	public AppOvertimeSettingDto findByCom(){
		Optional<AppOvertimeSetting> appOver = appOverRep.getAppOver(); 
		if(appOver.isPresent()){
			return AppOvertimeSettingDto.convertToDto(appOver.get());
		}
		return null;
	}
}
