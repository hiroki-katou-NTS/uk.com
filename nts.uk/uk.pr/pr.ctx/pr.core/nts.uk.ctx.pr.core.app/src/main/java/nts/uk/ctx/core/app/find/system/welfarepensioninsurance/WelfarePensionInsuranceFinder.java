package nts.uk.ctx.core.app.find.system.welfarepensioninsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.find.system.welfarepensioninsurance.dto.WelfarePensionInsuraceRateDto;
import nts.uk.ctx.core.app.find.system.welfarepensioninsurance.dto.WelfarePensionInsuranceRateHistoryDto;
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

@Stateless
public class WelfarePensionInsuranceFinder {
	@Inject
	private BonusEmployeePensionInsuranceRateRepository bonusEmployeePensionInsuranceRateRepository;
	
	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;
	
	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;
	
	@Inject
	private WelfarePensionInsuranceRateHistoryRepository welfarePensionInsuranceRateHistoryRepository;
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	public WelfarePensionInsuraceRateDto findWelfarePensionByHistoryID (String historyId){
		Optional<BonusEmployeePensionInsuranceRate> bonusEmployeePension = bonusEmployeePensionInsuranceRateRepository.getBonusEmployeePensionInsuranceRateById(historyId);
		Optional<EmployeesPensionMonthlyInsuranceFee> employeePensonMonthly = employeesPensionMonthlyInsuranceFeeRepository.getEmployeesPensionMonthlyInsuranceFeeByHistoryId(historyId);
		Optional<WelfarePensionInsuranceClassification> welfarePensionClassification = welfarePensionInsuranceClassificationRepository.getWelfarePensionInsuranceClassificationById(historyId);
		return new WelfarePensionInsuraceRateDto(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification);
	}
	
	public List<WelfarePensionInsuranceRateHistoryDto> findOfficeByCompanyId () {
		List<WelfarePensionInsuranceRateHistoryDto> socialInsuranceDtoList = new ArrayList<>();
		List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository.findByCid(AppContexts.user().companyId());
		socialInsuranceOfficeList.forEach(office -> {
			Optional<WelfarePensionInsuranceRateHistory> welfarePensionHistory = welfarePensionInsuranceRateHistoryRepository.getWelfarePensionInsuranceRateHistoryByOfficeCode(office.getCode().v());
			socialInsuranceDtoList.add(WelfarePensionInsuranceRateHistoryDto.fromDomainToDto(welfarePensionHistory, office.getName().v()));
		});
		return socialInsuranceDtoList;
	}
	
}
