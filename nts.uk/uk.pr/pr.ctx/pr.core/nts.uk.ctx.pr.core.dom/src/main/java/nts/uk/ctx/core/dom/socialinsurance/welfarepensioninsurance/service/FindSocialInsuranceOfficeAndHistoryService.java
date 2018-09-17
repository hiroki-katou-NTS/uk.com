package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class FindSocialInsuranceOfficeAndHistoryService {
	
	@Inject
	private WelfarePensionInsuranceRateHistoryRepository welfarePensionInsuranceRateHistoryRepository;
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	public void addNewHistory (String officeCode, YearMonthHistoryItem yearMonthItem) {
		WelfarePensionInsuranceRateHistory welfarePensionHistory = null;
		Optional<SocialInsuranceOffice> socialOffice = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), officeCode);
		if (!socialOffice.isPresent()){
			throw new BusinessException("Office not found");
		}
		Optional<WelfarePensionInsuranceRateHistory> opt_welfarePensionHistory = welfarePensionInsuranceRateHistoryRepository.getWelfarePensionInsuranceRateHistoryByOfficeCode(officeCode);
		if (opt_welfarePensionHistory.isPresent()){
			welfarePensionHistory = opt_welfarePensionHistory.get();
			if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) 
			welfarePensionHistory.add(yearMonthItem);
			welfarePensionInsuranceRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		} else{
			welfarePensionHistory = new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), officeCode, Arrays.asList(yearMonthItem));
		}
		welfarePensionInsuranceRateHistoryRepository.add(welfarePensionHistory);
	}
}
