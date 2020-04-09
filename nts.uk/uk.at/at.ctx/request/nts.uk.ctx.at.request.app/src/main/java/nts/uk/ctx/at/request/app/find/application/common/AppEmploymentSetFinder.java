package nts.uk.ctx.at.request.app.find.application.common;



import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppEmploymentSetFinder {
	@Inject
	private AppEmploymentSettingRepository appEmploymentSetRepository;
	
	/**
	 * Find all employment setting by company id
	 * @param companyId
	 * @return
	 */
	public List<AppEmploymentSettingDto> findEmploymentSetByCompanyId(){
		//会社ID
		String companyId =  AppContexts.user().companyId();
		List<AppEmploymentSetting> appEmploymentList1 = appEmploymentSetRepository.getEmploymentSetting(companyId);
		if(!CollectionUtil.isEmpty(appEmploymentList1)) {
			return AppEmploymentSettingDto.fromDomain(appEmploymentList1);
		}
//		if (!CollectionUtil.isEmpty(appEmploymentList)) {
//			return appEmploymentList.stream()
//					.map(item -> AppEmploymentSettingDto.fromDomain(item))
//					.collect(Collectors.toList());
//		} 
		return null;
	}
	/**
	 * Find all employment setting by company id and employment code
	 * @param employmentCode
	 * @return
	 */
	public List<AppEmploymentSettingDto> findEmploymentSetByCode(String employmentCode){
		//会社ID
		String companyId =  AppContexts.user().companyId();
		List<AppEmploymentSetting> appEmploymentList = appEmploymentSetRepository.getEmploymentSetting(companyId, employmentCode);
		
//		if (!CollectionUtil.isEmpty(appEmploymentList)) {
//			return appEmploymentList.stream()
//					.map(item -> AppEmploymentSettingDto.fromDomain(item))
//					.collect(Collectors.toList());
//		}
		if(!CollectionUtil.isEmpty(appEmploymentList)) {
			return AppEmploymentSettingDto.fromDomain(appEmploymentList);
		}
		return null;
	}
}
