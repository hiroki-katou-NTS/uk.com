package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.GradeWelfarePensionInsurancePremiumDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryEmployeesPensionInsuranceRateDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.StartCommandHealth;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensionGradePerRewardMonthlyRangeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensionStandardGradePerMonthDto;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;

@Transactional
public class WelfarePensionStandardMonthlyFeeFinder {

	@Inject
	private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	@Inject
	private MonthlyScopeOfWelfarePensionCompensationRepository monthlyScopeOfWelfarePensionCompensationRepository;

	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;

	public void findAllWelfarePensionAndRate(StartCommandHealth startCommand) {

		List<WelfarePensionStandardGradePerMonthDto> welfarePensionStandardGradePerMonthDtos = new ArrayList<>();
		List<WelfarePensionGradePerRewardMonthlyRangeDto> welfarePensionGradePerRewardMonthlyRangeDto = new ArrayList<>();
		SalaryEmployeesPensionInsuranceRateDto salaryEmployeesPensionInsuranceRateDto = new SalaryEmployeesPensionInsuranceRateDto();
		List<GradeWelfarePensionInsurancePremiumDto> gradeWelfarePensionInsurancePremiumDtos = new ArrayList<>();

		// ドメインモデル「厚生年金標準月額」を取得する
		Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
				.getWelfarePensionStandardMonthlyFeeByStartYearMonth(startCommand.getDate());
		if (welfarePensionStandardMonthlyFee.isPresent()) {
			welfarePensionStandardGradePerMonthDtos = welfarePensionStandardMonthlyFee.get().getStandardMonthlyPrice()
					.stream().map(x -> new WelfarePensionStandardGradePerMonthDto(x.getWelfarePensionGrade(),
							x.getStandardMonthlyFee()))
					.collect(Collectors.toList());
		}

		// ドメインモデル「厚生年金報酬月額範囲」を取得する
		Optional<MonthlyScopeOfWelfarePensionCompensation> monthlyScopeOfWelfarePensionCompensation = monthlyScopeOfWelfarePensionCompensationRepository
				.getMonthlyScopeOfWelfarePensionCompensationByStartYearMonth(startCommand.getDate());
		if (monthlyScopeOfWelfarePensionCompensation.isPresent()) {
			welfarePensionGradePerRewardMonthlyRangeDto = monthlyScopeOfWelfarePensionCompensation.get()
					.getWelfarePensionGradePerRewardMonthlyRange().stream()
					.map(x -> new WelfarePensionGradePerRewardMonthlyRangeDto(x.getWelfarePensionGrade(),
							x.getRewardMonthlyLowerLimit(), x.getRewardMonthlyUpperLimit()))
					.collect(Collectors.toList());
		}

		// ドメインモデル「厚生年金月額保険料額」を取得する
		Optional<EmployeesPensionMonthlyInsuranceFee> employeesPensionMonthlyInsuranceFee = employeesPensionMonthlyInsuranceFeeRepository
				.getEmployeesPensionMonthlyInsuranceFeeByHistoryId(startCommand.getHistoryId());
		if(employeesPensionMonthlyInsuranceFee.isPresent()) {
			salaryEmployeesPensionInsuranceRateDto = new SalaryEmployeesPensionInsuranceRateDto(employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualBurdenRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualBurdenRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeContributionRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeContributionRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualExemptionRate().get().v().toString() : null, 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualExemptionRate().get().v().toString() : null, 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeExemptionRate().get().v().toString() : null , 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeExemptionRate().get().v().toString() : null);
			
			gradeWelfarePensionInsurancePremiumDtos = employeesPensionMonthlyInsuranceFee.get().getPensionInsurancePremium().stream().map(x -> new GradeWelfarePensionInsurancePremiumDto(x.getInsuredBurden().getMaleInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getMaleInsurancePremium().v().toString(), 
					x.getInsuredBurden().getMaleExemptionInsurance().isPresent() ? x.getInsuredBurden().getMaleExemptionInsurance().get().v().toString() : null, 
					x.getEmployeeBurden().getMaleExemptionInsurance().isPresent() ? x.getEmployeeBurden().getMaleExemptionInsurance().get().v().toString() :null, 
					x.getInsuredBurden().getFemaleInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getFemaleInsurancePremium().v().toString(), 
					x.getInsuredBurden().getFemaleExemptionInsurance().isPresent() ? x.getInsuredBurden().getFemaleExemptionInsurance().get().v().toString() : null, 
					x.getEmployeeBurden().getFemaleExemptionInsurance().isPresent() ? x.getEmployeeBurden().getFemaleExemptionInsurance().get().v().toString() : null)).collect(Collectors.toList());
		}

	}
	
	
	
	
}
