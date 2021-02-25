package nts.uk.ctx.at.request.app.find.application.common;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting.AppEmploymentSetDto;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppEmploymentSetFinder {
//	@Inject
//	private AppEmploymentSettingRepository appEmploymentSetRepository;

	// refactor 4
	@Inject
	private AppEmploymentSetRepository appEmploymentSetRepo;
	
	/**
	 * Find all employment setting by company id
	 * @return
	 */
//	public List<AppEmploymentSettingDto> findEmploymentSetByCompanyId(){
//		//会社ID
//		String companyId =  AppContexts.user().companyId();
//		List<AppEmploymentSetting> appEmploymentList1 = appEmploymentSetRepository.getEmploymentSetting(companyId);
//		if(!CollectionUtil.isEmpty(appEmploymentList1)) {
//			return AppEmploymentSettingDto.fromDomain(appEmploymentList1);
//		}
//
//		return new ArrayList<AppEmploymentSettingDto>();
//	}

	/**
	 * 起動初期の処理
	 * ドメイン「雇用別申請承認設定」を取得する
	 */
	public List<AppEmploymentSetDto> findAllEmploymentSetting() {
	    String companyId = AppContexts.user().companyId();
	    return appEmploymentSetRepo.findByCompanyID(companyId).stream().map(AppEmploymentSetDto::fromDomain).collect(Collectors.toList());
    }

	/**
	 * Find all employment setting by company id and employment code
	 * @param employmentCode
	 * @return
	 */
//	public AppEmploymentSettingDto findEmploymentSetByCode(String employmentCode){
//		//会社ID
//		String companyId =  AppContexts.user().companyId();
//		Optional<AppEmploymentSetting> appEmploymentList = appEmploymentSetRepository.getEmploymentSetting(companyId, employmentCode);
//
//		if(appEmploymentList.isPresent()) {
//			return AppEmploymentSettingDto.fromDomain(appEmploymentList);
//		}
//		return null;
//	}

	public AppEmploymentSetDto findByEmployment(String employmentCode) {
		String companyId = AppContexts.user().companyId();
		return appEmploymentSetRepo.findByCompanyIDAndEmploymentCD(companyId, employmentCode).map(AppEmploymentSetDto::fromDomain).orElse(null);
	}
}
