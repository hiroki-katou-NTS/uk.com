package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CusHealthInsuDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuranceGradePerRewardMonthlyRangeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsurancePerGradeFeeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuranceStandardGradePerMonthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthInsurancePremiumRateDto;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensationRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class HealthInsuStandardMonthlyFinder {
	
	@Inject
	private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;
	
	@Inject
	private MonthlyHealthInsuranceCompensationRepository monthlyHealthInsuranceCompensationRepository;
	
	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;
	
	
	public SalaryHealthDto initScreen(int date, String historyId, Boolean check) {
		
		List<HealthInsuranceStandardGradePerMonthDto> healthInsuranceStandardGradePerMonthDtos = new ArrayList<>();
		List<HealthInsuranceGradePerRewardMonthlyRangeDto> healthInsuranceGradePerRewardMonthlyRangesDtos = new ArrayList<>();
		
		// ドメインモデル「健康保険標準月額」を取得する
		Optional<HealthInsuranceStandardMonthly> healthInsuStandarMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(date);
		if (healthInsuStandarMonthly.isPresent())
			healthInsuranceStandardGradePerMonthDtos = healthInsuStandarMonthly.get().getStandardGradePerMonth()
					.stream().map(x -> new HealthInsuranceStandardGradePerMonthDto(x.getHealthInsuranceGrade(),
							x.getStandardMonthlyFee()))
					.collect(Collectors.toList());

		// ドメインモデル「健康保険報酬月額範囲」を取得する
		Optional<MonthlyHealthInsuranceCompensation> dataMonth = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonthCom(date);

		if (dataMonth.isPresent())
			healthInsuranceGradePerRewardMonthlyRangesDtos = dataMonth.get()
					.getHealthInsuranceGradePerRewardMonthlyRange().stream()
					.map(x -> new HealthInsuranceGradePerRewardMonthlyRangeDto(x.getHealthInsuranceGrade(),
							x.getRewardMonthlyLowerLimit(), x.getRewardMonthlyUpperLimit()))
					.collect(Collectors.toList());
		
		
		// ドメインモデル「健康保険月額保険料額」を取得する
		SalaryHealthInsurancePremiumRateDto salaryHealthInsurancePremiumRateDto = new SalaryHealthInsurancePremiumRateDto();
		List<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFeeDtos = new ArrayList<>();
		Optional<HealthInsuranceMonthlyFee> dataFee = healthInsuranceMonthlyFeeRepository.getHealthInsuranceMonthlyFeeById(historyId);
		
		
		if(dataFee.isPresent()) {
			if(check) {
				dataFee.get().algorithmMonthlyHealthInsurancePremiumCalculation(healthInsuStandarMonthly);
			}
			healthInsurancePerGradeFeeDtos = dataFee.get().getHealthInsurancePerGradeFee().stream().map(x -> new HealthInsurancePerGradeFeeDto(x.getHealthInsuranceGrade(), 
					x.getInsuredBurden().getHealthInsurancePremium().v().toString(), 
					x.getInsuredBurden().getNursingCare().v().toString(), 
					x.getInsuredBurden().getSpecInsurancePremium().v().toString(), 
					x.getInsuredBurden().getBasicInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getHealthInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getNursingCare().v().toString(), 
					x.getEmployeeBurden().getSpecInsurancePremium().v().toString(), 
					x.getEmployeeBurden().getBasicInsurancePremium().v().toString())).collect(Collectors.toList());
			
			
			salaryHealthInsurancePremiumRateDto = new SalaryHealthInsurancePremiumRateDto(TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getHealthInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getLongCareInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getSpecialInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getBasicInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getHealthInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getLongCareInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getSpecialInsuranceRate().v().toString()),
					TextResource.localize("QMM008_156",dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getBasicInsuranceRate().v().toString()));
		}
		List<CusHealthInsuDto> response = mappingArray(healthInsuranceStandardGradePerMonthDtos,healthInsuranceGradePerRewardMonthlyRangesDtos,healthInsurancePerGradeFeeDtos);
		return new SalaryHealthDto(response,salaryHealthInsurancePremiumRateDto);
		
	}
	
	private List<CusHealthInsuDto> mappingArray(
			List<HealthInsuranceStandardGradePerMonthDto> healthInsuranceStandardGradePerMonthDtos,
			List<HealthInsuranceGradePerRewardMonthlyRangeDto> healthInsuranceGradePerRewardMonthlyRangesDtos,
			List<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFeeDtos) {
		List<CusHealthInsuDto> response = new ArrayList<>();
		for (HealthInsuranceStandardGradePerMonthDto standardGradePerMonthDto : healthInsuranceStandardGradePerMonthDtos) {
			Optional<HealthInsuranceGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRangesDtos = healthInsuranceGradePerRewardMonthlyRangesDtos.stream().filter( x -> x.getHealthInsuranceGrade() == standardGradePerMonthDto.getHealthInsuranceGrade()).findFirst();
			Optional<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFeeDto = healthInsurancePerGradeFeeDtos.stream().filter(x -> x.getHealthInsuranceGrade() == standardGradePerMonthDto.getHealthInsuranceGrade()).findFirst();
			CusHealthInsuDto data = new CusHealthInsuDto(standardGradePerMonthDto.getHealthInsuranceGrade(), 
					standardGradePerMonthDto.getStandardMonthlyFee(), 
					gradePerRewardMonthlyRangesDtos.isPresent() ? gradePerRewardMonthlyRangesDtos.get().getRewardMonthlyLowerLimit() : null, 
					gradePerRewardMonthlyRangesDtos.isPresent() ? gradePerRewardMonthlyRangesDtos.get().getRewardMonthlyUpperLimit() : null, 
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getInHealthInsurancePremium() : null, 
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getInNursingCare() : null, 
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getInSpecInsurancePremium() : null,
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getInBasicInsurancePremium() : null,
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getEmHealthInsurancePremium() : null,
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getEmNursingCare() : null,
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getEmSpecInsurancePremium() : null,
					healthInsurancePerGradeFeeDto.isPresent() ? healthInsurancePerGradeFeeDto.get().getEmBasicInsurancePremium() : null);
			response.add(data);
		}
		return response;
	}
	
}
