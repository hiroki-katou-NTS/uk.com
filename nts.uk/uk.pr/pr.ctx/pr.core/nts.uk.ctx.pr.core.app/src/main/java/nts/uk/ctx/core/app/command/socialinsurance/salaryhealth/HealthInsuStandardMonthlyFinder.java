package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuPerGradeFeeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuranceGradePerRewardMonthlyRangeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuranceStandardGradePerMonthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseHealthAndMonthly;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsurancePerGradeFeeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthInsurancePremiumRateDto;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;
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
				HealthInsurancePerGradeFeeDto healthInsurancePerGradeFeeDto = new HealthInsurancePerGradeFeeDto(dataFee.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getHealthInsurancePremium().v().toString(),
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
		return new SalaryHealthDto(healthInsuranceGradePerRewardMonthlyRangesDtos,
				healthInsuranceStandardGradePerMonthDtos, healthInsurancePerGradeFeeDtos,
				salaryHealthInsurancePremiumRateDto);
		
	}
	
	
	public List<HealthInsuPerGradeFeeDto> update(String historyId) {
		
		// ドメインモデル「健康保険月額保険料額」を更新する
		List<HealthInsuPerGradeFeeDto> response = new ArrayList<>();
		Optional<HealthInsuranceMonthlyFee> healthInsuran = healthInsuranceMonthlyFeeRepository.getHealthInsuranceMonthlyFeeById(historyId);
		if (healthInsuran.isPresent()) {
			for (int i = 0; i < healthInsuran.get().getHealthInsurancePerGradeFee().size(); i++) {
				HealthInsuPerGradeFeeDto data = new HealthInsuPerGradeFeeDto(healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getHealthInsurancePremium().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getNursingCare().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getSpecInsurancePremium().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getInsuredBurden().getBasicInsurancePremium().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getHealthInsurancePremium().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getNursingCare().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getSpecInsurancePremium().v().toString(), 
						healthInsuran.get().getHealthInsurancePerGradeFee().get(i).getEmployeeBurden().getBasicInsurancePremium().v().toString());
				response.add(data);
			}

		}
		return response;
	}
	
	
}
