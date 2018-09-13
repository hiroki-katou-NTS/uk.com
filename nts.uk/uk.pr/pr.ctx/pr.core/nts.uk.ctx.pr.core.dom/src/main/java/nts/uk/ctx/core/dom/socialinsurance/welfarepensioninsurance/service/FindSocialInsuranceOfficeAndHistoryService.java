package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service;

import java.util.Arrays;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

public class FindSocialInsuranceOfficeAndHistoryService {
	
	@Inject
	private WelfarePensionInsuranceRateHistoryRepository welfarePensionInsuranceRateHistoryRepository;
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	public void addNewHistory (String officeCode, YearMonthHistoryItem yearMonthItem) {
		WelfarePensionInsuranceRateHistory welfarePensionHistory = null;
		Optional<SocialInsuranceOffice> socialOffice = socialInsuranceOfficeRepository.findById(AppContexts.user().companyId(), officeCode);
		if (!socialOffice.isPresent()){
			throw new BusinessException("This office has been deleded");
		}
		Optional<WelfarePensionInsuranceRateHistory> opt_welfarePensionHistory = welfarePensionInsuranceRateHistoryRepository.getWelfarePensionInsuranceRateHistoryByOfficeCode(officeCode);
		if (opt_welfarePensionHistory.isPresent()){
			welfarePensionHistory = opt_welfarePensionHistory.get();
			welfarePensionHistory.add(yearMonthItem);
			welfarePensionInsuranceRateHistoryRepository.remove(welfarePensionHistory);
		} else{
			welfarePensionHistory = new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), officeCode, Arrays.asList(yearMonthItem));
		}
		welfarePensionInsuranceRateHistoryRepository.add(welfarePensionHistory);
	}
}
