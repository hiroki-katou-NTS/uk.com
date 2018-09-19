package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CountCommand;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CusDataDto;
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

@Stateless
public class HealthInsuStandardMonthlyFinder {
	
	@Inject
	private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;
	
	@Inject
	private MonthlyHealthInsuranceCompensationRepository monthlyHealthInsuranceCompensationRepository;
	
	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;
	
	
	public SalaryHealthDto initScreen(int date, String historyId) {
		
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
		Optional<MonthlyHealthInsuranceCompensation> dataMonth = monthlyHealthInsuranceCompensationRepository
				.findByDate(date);
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
			for (int i = 0; i < dataFee.get().getHealthInsurancePerGradeFee().size(); i++) {
				HealthInsurancePerGradeFeeDto healthInsurancePerGradeFeeDto = new HealthInsurancePerGradeFeeDto(dataFee.get().getHealthInsurancePerGradeFee().get(i).getHealthInsuranceGrade(),dataFee.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getHealthInsurancePremium().v().toString(),
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getNursingCare().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getSpecInsurancePremium().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getBasicInsurancePremium().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getHealthInsurancePremium().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getNursingCare().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getSpecInsurancePremium().v().toString(), 
						dataFee.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getBasicInsurancePremium().v().toString());
				healthInsurancePerGradeFeeDtos.add(healthInsurancePerGradeFeeDto);
			}
			salaryHealthInsurancePremiumRateDto = new SalaryHealthInsurancePremiumRateDto(dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getHealthInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getLongCareInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getSpecialInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getBasicInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getHealthInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getLongCareInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getSpecialInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getBasicInsuranceRate().v().toString());
		}
		List<CusDataDto> response = mappingArray(healthInsuranceStandardGradePerMonthDtos,healthInsuranceGradePerRewardMonthlyRangesDtos,healthInsurancePerGradeFeeDtos);
		return new SalaryHealthDto(response,salaryHealthInsurancePremiumRateDto);
		
	}
	
	private List<CusDataDto> mappingArray(
			List<HealthInsuranceStandardGradePerMonthDto> healthInsuranceStandardGradePerMonthDtos,
			List<HealthInsuranceGradePerRewardMonthlyRangeDto> healthInsuranceGradePerRewardMonthlyRangesDtos,
			List<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFeeDtos) {
		List<CusDataDto> response = new ArrayList<>();
		for (int i = 0; i < healthInsuranceStandardGradePerMonthDtos.size(); i++) {
			int count = i;
			Optional<HealthInsuranceGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRangesDtos = healthInsuranceGradePerRewardMonthlyRangesDtos.stream().filter( x -> x.getHealthInsuranceGrade() == healthInsuranceStandardGradePerMonthDtos.get(count).getHealthInsuranceGrade()).findFirst();
			Optional<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFeeDto = healthInsurancePerGradeFeeDtos.stream().filter(x -> x.getHealthInsuranceGrade() == healthInsuranceStandardGradePerMonthDtos.get(count).getHealthInsuranceGrade()).findFirst();
			CusDataDto data = new CusDataDto(healthInsuranceStandardGradePerMonthDtos.get(i).getHealthInsuranceGrade(), 
					healthInsuranceStandardGradePerMonthDtos.get(i).getStandardMonthlyFee(), 
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
	
	public SalaryHealthDto countHealthRate(CountCommand countCommand) {
		
		List<CusDataDto> cusDataDtos = countCommand.getCusDataDtos();
		SalaryHealthInsurancePremiumRateDto premiumRate = countCommand.getPremiumRate();
		Double finHealthInsurancePremium;
		for (CusDataDto cusDataDto : cusDataDtos) {
			finHealthInsurancePremium = cusDataDto.getStandardMonthlyFee()*(new BigDecimal(premiumRate.getInHealthInsuranceRate())).doubleValue();
			System.out.print(finHealthInsurancePremium);
		}
		
		return null;
	}
	
}
