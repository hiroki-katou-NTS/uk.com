package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseHealthAndMonthly;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseHealthInsurancePerGradeFee;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseSalaryHealth;
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
	
	
	public ResponseSalaryHealth initScreen(int date, String historyId) {
		
		List<ResponseHealthAndMonthly> dataList = new ArrayList<>();
		List<HealthInsuranceGradePerRewardMonthlyRange> healthInsuranceGradePerRewardMonthlyRanges = new ArrayList<>();
		
		// ドメインモデル「健康保険標準月額」を取得する
		Optional<HealthInsuranceStandardMonthly> healthInsuStandarMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(date);
		if(healthInsuStandarMonthly.isPresent())
		dataList = healthInsuStandarMonthly.get().getStandardGradePerMonth().stream().map(x -> new ResponseHealthAndMonthly(x.getHealthInsuranceGrade(), null, null, x.getStandardMonthlyFee(),null,null,null,null,null,null,null,null)).collect(Collectors.toList());
		
		// ドメインモデル「健康保険報酬月額範囲」を取得する
		Optional<MonthlyHealthInsuranceCompensation> dataMonth = monthlyHealthInsuranceCompensationRepository
				.findByDate(date);
		if(dataMonth.isPresent())
		healthInsuranceGradePerRewardMonthlyRanges = dataMonth.get()
				.getHealthInsuranceGradePerRewardMonthlyRange();
		for (ResponseHealthAndMonthly responseHealthAndMonthly : dataList) {
			for (HealthInsuranceGradePerRewardMonthlyRange healthInsuranceGradePerRewardMonthlyRange : healthInsuranceGradePerRewardMonthlyRanges) {
				if(responseHealthAndMonthly.getHealthInsuranceGrade() == healthInsuranceGradePerRewardMonthlyRange.getHealthInsuranceGrade())
					responseHealthAndMonthly.setRewardMonthlyLowerLimit(healthInsuranceGradePerRewardMonthlyRange.getRewardMonthlyLowerLimit());
					responseHealthAndMonthly.setRewardMonthlyUpperLimit(healthInsuranceGradePerRewardMonthlyRange.getRewardMonthlyUpperLimit());
			}
		}
		
		// ドメインモデル「健康保険月額保険料額」を取得する
		List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee = new ArrayList<>();
		Optional<HealthInsuranceMonthlyFee> dataFee = healthInsuranceMonthlyFeeRepository.getHealthInsuranceMonthlyFeeById(historyId);
		ResponseHealthInsurancePerGradeFee responseHealthInsurancePerGradeFee = new ResponseHealthInsurancePerGradeFee();
		if(dataFee.isPresent()) {
			 responseHealthInsurancePerGradeFee = new ResponseHealthInsurancePerGradeFee(
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getBasicInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getLongCareInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getSpecialInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getIndividualBurdenRatio().getBasicInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getHealthInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getLongCareInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getSpecialInsuranceRate().v().toString(), 
					dataFee.get().getHealthInsuranceRate().getEmployeeBurdenRatio().getBasicInsuranceRate().v().toString());
			
			healthInsurancePerGradeFee = dataFee.get().getHealthInsurancePerGradeFee();
		}
		for (ResponseHealthAndMonthly responseHealthAndMonthly : dataList) {
			for (HealthInsurancePerGradeFee healthInsurance : healthInsurancePerGradeFee) {
				if(responseHealthAndMonthly.getHealthInsuranceGrade() == healthInsurance.getHealthInsuranceGrade()) {
					responseHealthAndMonthly.setEmBasicInsurancePremium(healthInsurance.getEmployeeBurden().getBasicInsurancePremium().v());
					responseHealthAndMonthly.setEmHealthInsurancePremium(healthInsurance.getEmployeeBurden().getHealthInsurancePremium().v());
					responseHealthAndMonthly.setEmNursingCare(healthInsurance.getEmployeeBurden().getNursingCare().v());
					responseHealthAndMonthly.setEmSpecInsurancePremium(healthInsurance.getEmployeeBurden().getSpecInsurancePremium().v());
					responseHealthAndMonthly.setInBasicInsurancePremium(healthInsurance.getInsuredBurden().getBasicInsurancePremium().v());
					responseHealthAndMonthly.setInHealthInsurancePremium(healthInsurance.getInsuredBurden().getHealthInsurancePremium().v());
					responseHealthAndMonthly.setInNursingCare(healthInsurance.getInsuredBurden().getNursingCare().v());
					responseHealthAndMonthly.setInSpecInsurancePremium(healthInsurance.getInsuredBurden().getSpecInsurancePremium().v());
				}
			}
		}
		return new ResponseSalaryHealth(responseHealthInsurancePerGradeFee, dataList);
		
		
		
	}
	
}
