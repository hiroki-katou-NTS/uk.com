package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
@Data
@AllArgsConstructor
public class EmployeesPensionMonthlyInsuranceFeeDto {
	
	/**
     * 履歴ID
     */
    private String historyId;

    /**
     * 自動計算区分
     */
    private int autoCalculationCls;

    /**
     * 等級毎厚生年金保険料
     */
    private List<GradeWelfarePensionInsurancePremiumDto> pensionInsurancePremium;

    /**
     * 給与厚生年金保険料率
     */
    private SalaryEmployeesPensionInsuranceRateDto salaryEmployeesPensionInsuranceRate;
    
	public static EmployeesPensionMonthlyInsuranceFeeDto fromDomainToDto(EmployeesPensionMonthlyInsuranceFee domain){
		List<GradeWelfarePensionInsurancePremiumDto> pensionInsurancePremium = domain.getPensionInsurancePremium().stream().map(pensionItem ->{
			return GradeWelfarePensionInsurancePremiumDto.fromDomainToDto(pensionItem);
		}).collect(Collectors.toList());
		return new EmployeesPensionMonthlyInsuranceFeeDto(domain.getHistoryId(), domain.getAutoCalculationCls().value, pensionInsurancePremium, SalaryEmployeesPensionInsuranceRateDto.fromDomainToDto(domain.getSalaryEmployeesPensionInsuranceRate()));
	}
}
