package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.GradeWelfarePensionInsurancePremiumDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseWelfarePension;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryEmployeesPensionInsuranceRateDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.StartCommandHealth;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensionGradePerRewardMonthlyRangeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensionStandardGradePerMonthDto;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionByGrade;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;

@Transactional
@Stateless
public class WelfarePensionStandardMonthlyFeeFinder {

	@Inject
	private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	@Inject
	private MonthlyScopeOfWelfarePensionCompensationRepository monthlyScopeOfWelfarePensionCompensationRepository;

	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;
	
	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;
	
	@Inject
	private ContributionRateRepository contributionRateRepository;


	public ResponseWelfarePension findAllWelfarePensionAndRate(StartCommandHealth startCommand, Boolean check) {

		List<WelfarePensionStandardGradePerMonthDto> welfarePensionStandardGradePerMonthDtos = new ArrayList<>();
		List<WelfarePensionGradePerRewardMonthlyRangeDto> welfarePensionGradePerRewardMonthlyRangeDtos = new ArrayList<>();
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
			welfarePensionGradePerRewardMonthlyRangeDtos = monthlyScopeOfWelfarePensionCompensation.get()
					.getWelfarePensionGradePerRewardMonthlyRange().stream()
					.map(x -> new WelfarePensionGradePerRewardMonthlyRangeDto(x.getWelfarePensionGrade(),
							x.getRewardMonthlyLowerLimit(), x.getRewardMonthlyUpperLimit()))
					.collect(Collectors.toList());
		}

		// ドメインモデル「厚生年金月額保険料額」を取得する
		Optional<EmployeesPensionMonthlyInsuranceFee> employeesPensionMonthlyInsuranceFee = employeesPensionMonthlyInsuranceFeeRepository
				.getEmployeesPensionMonthlyInsuranceFeeByHistoryId(startCommand.getHistoryId());
		Optional<WelfarePensionInsuranceClassification> welfarePensionInsuranceCls = welfarePensionInsuranceClassificationRepository.getWelfarePensionInsuranceClassificationById(startCommand.getHistoryId());
		if(employeesPensionMonthlyInsuranceFee.isPresent()) {
			if(check && welfarePensionInsuranceCls.isPresent()) {
				employeesPensionMonthlyInsuranceFee.get().algorithmMonthlyWelfarePensionInsuranceFeeCalculation(welfarePensionStandardMonthlyFee, welfarePensionInsuranceCls.get());
			}
			salaryEmployeesPensionInsuranceRateDto = new SalaryEmployeesPensionInsuranceRateDto(employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualBurdenRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualBurdenRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeContributionRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeContributionRatio().v().toString(), 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualExemptionRate().get().v().toString() : null, 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualExemptionRate().get().v().toString() : null, 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeExemptionRate().get().v().toString() : null , 
					employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeExemptionRate().isPresent() ? employeesPensionMonthlyInsuranceFee.get().getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeExemptionRate().get().v().toString() : null);
			
			gradeWelfarePensionInsurancePremiumDtos = employeesPensionMonthlyInsuranceFee.get().getPensionInsurancePremium().stream().map(x -> new GradeWelfarePensionInsurancePremiumDto(x.getWelfarePensionGrade(),x.getInsuredBurden().getMaleInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getMaleInsurancePremium().v().toString(), 
					x.getInsuredBurden().getMaleExemptionInsurance().isPresent() ? x.getInsuredBurden().getMaleExemptionInsurance().get().v().toString() : null, 
					x.getEmployeeBurden().getMaleExemptionInsurance().isPresent() ? x.getEmployeeBurden().getMaleExemptionInsurance().get().v().toString() :null, 
					x.getInsuredBurden().getFemaleInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getFemaleInsurancePremium().v().toString(), 
					x.getInsuredBurden().getFemaleExemptionInsurance().isPresent() ? x.getInsuredBurden().getFemaleExemptionInsurance().get().v().toString() : null, 
					x.getEmployeeBurden().getFemaleExemptionInsurance().isPresent() ? x.getEmployeeBurden().getFemaleExemptionInsurance().get().v().toString() : null)).collect(Collectors.toList());
		}
		Optional<WelfarePensionInsuranceClassification> insuranceClassification = welfarePensionInsuranceClassificationRepository.getWelfarePensionInsuranceClassificationById(startCommand.getHistoryId());
		Boolean display = false;
		if(insuranceClassification.isPresent() && insuranceClassification.get().getFundClassification().value == 1) {
			display = true;
		} 
		List<CusWelfarePensionDto> response = mapping(welfarePensionStandardGradePerMonthDtos, welfarePensionGradePerRewardMonthlyRangeDtos, gradeWelfarePensionInsurancePremiumDtos);
		return new ResponseWelfarePension(salaryEmployeesPensionInsuranceRateDto, response,display);
	}
	
	public List<CusWelfarePensionDto> mapping(List<WelfarePensionStandardGradePerMonthDto> standardGradePerMonthDtos,
			List<WelfarePensionGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRangeDtos,
			List<GradeWelfarePensionInsurancePremiumDto> insurancePremiumDtos) {
		
		List<CusWelfarePensionDto> response = new ArrayList<>();
		for (WelfarePensionStandardGradePerMonthDto standardGradePerMonthDto : standardGradePerMonthDtos) {
			Optional<WelfarePensionGradePerRewardMonthlyRangeDto> rewardMonthlyRange = gradePerRewardMonthlyRangeDtos.stream().filter(x -> x.getWelfarePensionGrade() == standardGradePerMonthDto.getWelfarePensionGrade()).findFirst();
			Optional<GradeWelfarePensionInsurancePremiumDto> nsurancePremium = insurancePremiumDtos.stream().filter(x -> x.getWelfarePensionGrade() == standardGradePerMonthDto.getWelfarePensionGrade()).findFirst();
			CusWelfarePensionDto data = new CusWelfarePensionDto(standardGradePerMonthDto.getWelfarePensionGrade(), 
					standardGradePerMonthDto.getStandardMonthlyFee(), 
					rewardMonthlyRange.isPresent() ? rewardMonthlyRange.get().getRewardMonthlyLowerLimit(): null, 
					rewardMonthlyRange.isPresent() ? rewardMonthlyRange.get().getRewardMonthlyUpperLimit() : null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getInMaleInsurancePremium(): null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getEmMaleInsurancePremium(): null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getInMaleExemptionInsurance(): null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getEmMaleExemptionInsurance(): null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getInFemaleInsurancePremium():null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getEmFemaleInsurancePremium():null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getInFemaleExemptionInsurance() : null, 
					nsurancePremium.isPresent() ? nsurancePremium.get().getEmFemaleExemptionInsurance() : null);
			response.add(data);
		}
		return response;
	}
	
	
	public List<CusWelfarePensionStandardDto> findAllWelfarePensionAndContributionRate(StartCommandHealth startCommand, Boolean check) {
		List<WelfarePensionStandardGradePerMonthDto> standardGradePerMonthDtos = new ArrayList<>();
		List<WelfarePensionGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRangeDtos = new ArrayList<>();
		List<ContributionByGrade> contributionByGrades = new ArrayList<>();
		// ドメインモデル「厚生年金標準月額」を取得する
		Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
				.getWelfarePensionStandardMonthlyFeeByStartYearMonth(startCommand.getDate());
		if (welfarePensionStandardMonthlyFee.isPresent()) {
			standardGradePerMonthDtos = welfarePensionStandardMonthlyFee.get().getStandardMonthlyPrice()
					.stream().map(x -> new WelfarePensionStandardGradePerMonthDto(x.getWelfarePensionGrade(),
							x.getStandardMonthlyFee()))
					.collect(Collectors.toList());
		}

		// ドメインモデル「厚生年金報酬月額範囲」を取得する
		Optional<MonthlyScopeOfWelfarePensionCompensation> monthlyScopeOfWelfarePensionCompensation = monthlyScopeOfWelfarePensionCompensationRepository
				.getMonthlyScopeOfWelfarePensionCompensationByStartYearMonth(startCommand.getDate());
		if (monthlyScopeOfWelfarePensionCompensation.isPresent()) {
			gradePerRewardMonthlyRangeDtos = monthlyScopeOfWelfarePensionCompensation.get()
					.getWelfarePensionGradePerRewardMonthlyRange().stream()
					.map(x -> new WelfarePensionGradePerRewardMonthlyRangeDto(x.getWelfarePensionGrade(),
							x.getRewardMonthlyLowerLimit(), x.getRewardMonthlyUpperLimit()))
					.collect(Collectors.toList());
		}
		
		// ドメインモデル「拠出金率」を取得する
		Optional<ContributionRate> contributionRate = contributionRateRepository.getContributionRateByHistoryId(startCommand.getHistoryId());
		if(contributionRate.isPresent()) {
			if(check) {
				contributionRate.get().algorithmWelfarePensionInsurancePremiumCal(welfarePensionStandardMonthlyFee, contributionRate.get());
			}
			contributionByGrades = contributionRate.get().getContributionByGrade();
		}
		return mappingToResponse(standardGradePerMonthDtos, gradePerRewardMonthlyRangeDtos, contributionByGrades);
	}
	
	
	public List<CusWelfarePensionStandardDto> mappingToResponse(List<WelfarePensionStandardGradePerMonthDto> standardGradePerMonthDtos,
			List<WelfarePensionGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRangeDtos,
			List<ContributionByGrade> contributionByGrades) {
			
		List<CusWelfarePensionStandardDto> response = new ArrayList<>();
		for (WelfarePensionStandardGradePerMonthDto standardGradePerMonthDto : standardGradePerMonthDtos) {
			Optional<WelfarePensionGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRange = gradePerRewardMonthlyRangeDtos.stream().filter(x -> x.getWelfarePensionGrade() == standardGradePerMonthDto.getWelfarePensionGrade()).findFirst();
			Optional<ContributionByGrade> contributionGrade = contributionByGrades.stream().filter(x -> x.getWelfarePensionGrade() == standardGradePerMonthDto.getWelfarePensionGrade()).findFirst();
			CusWelfarePensionStandardDto data = new CusWelfarePensionStandardDto(standardGradePerMonthDto.getWelfarePensionGrade(), 
					standardGradePerMonthDto.getStandardMonthlyFee(), 
					gradePerRewardMonthlyRange.isPresent() ? gradePerRewardMonthlyRange.get().getRewardMonthlyLowerLimit() : null, 
					gradePerRewardMonthlyRange.isPresent() ? gradePerRewardMonthlyRange.get().getRewardMonthlyUpperLimit() : null, 
					contributionGrade.isPresent() ? contributionGrade.get().getChildCareContribution().v().toString() : null);
			response.add(data);
		}
		return response;
	}
	
}
