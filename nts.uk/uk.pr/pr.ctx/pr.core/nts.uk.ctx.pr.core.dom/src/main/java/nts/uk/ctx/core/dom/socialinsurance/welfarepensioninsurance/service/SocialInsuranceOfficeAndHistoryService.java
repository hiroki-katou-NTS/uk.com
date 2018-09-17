package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class SocialInsuranceOfficeAndHistoryService {
	
	@Inject
	private WelfarePensionInsuranceRateHistoryRepository welfarePensionInsuranceRateHistoryRepository;
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Inject
	private BonusEmployeePensionInsuranceRateRepository bonusEmployeePensionInsuranceRateRepository;
	
	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;
	
	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;
	
	
	public void addNewHistory (String officeCode, YearMonthHistoryItem yearMonthItem, BonusEmployeePensionInsuranceRate bonusEmployeePension, EmployeesPensionMonthlyInsuranceFee employeePensonMonthly, WelfarePensionInsuranceClassification welfarePensionClassification) {
		WelfarePensionInsuranceRateHistory welfarePensionHistory = null;
		Optional<SocialInsuranceOffice> socialOffice = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), officeCode);
		if (!socialOffice.isPresent()){
			throw new BusinessException("Office not found");
		}
		Optional<WelfarePensionInsuranceRateHistory> opt_welfarePensionHistory = welfarePensionInsuranceRateHistoryRepository.getWelfarePensionInsuranceRateHistoryByOfficeCode(officeCode);
		if (opt_welfarePensionHistory.isPresent()){
			welfarePensionHistory = opt_welfarePensionHistory.get();
			if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) {
				welfarePensionHistory.add(yearMonthItem);
				bonusEmployeePensionInsuranceRateRepository.add(bonusEmployeePension);
				employeesPensionMonthlyInsuranceFeeRepository.add(employeePensonMonthly);
				welfarePensionInsuranceClassificationRepository.add(welfarePensionClassification);
			} else {
				bonusEmployeePensionInsuranceRateRepository.update(bonusEmployeePension);
				employeesPensionMonthlyInsuranceFeeRepository.update(employeePensonMonthly);
				welfarePensionInsuranceClassificationRepository.update(welfarePensionClassification);
			}
			welfarePensionInsuranceRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		} else{
			welfarePensionHistory = new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), officeCode, Arrays.asList(yearMonthItem));
		}
		welfarePensionInsuranceRateHistoryRepository.add(welfarePensionHistory);
	}
}
