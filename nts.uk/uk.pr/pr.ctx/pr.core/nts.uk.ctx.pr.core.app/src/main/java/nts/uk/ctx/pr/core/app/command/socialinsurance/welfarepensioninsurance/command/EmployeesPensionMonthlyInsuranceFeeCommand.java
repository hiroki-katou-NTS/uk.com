package nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;
@Data
@AllArgsConstructor
public class EmployeesPensionMonthlyInsuranceFeeCommand {
	
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
    private List<GradeWelfarePensionInsurancePremiumCommand> pensionInsurancePremium;

    /**
     * 給与厚生年金保険料率
     */
    private SalaryEmployeesPensionInsuranceRateCommand salaryEmployeesPensionInsuranceRate;
    
	public EmployeesPensionMonthlyInsuranceFee fromCommandToDomain(){
		List<GradeWelfarePensionInsurancePremium> pensionInsurancePremium = this.pensionInsurancePremium.stream().map(pensionItem ->{
			return pensionItem.fromDomainToDto();
		}).collect(Collectors.toList());
		return new EmployeesPensionMonthlyInsuranceFee(this.historyId, this.autoCalculationCls, pensionInsurancePremium, this.salaryEmployeesPensionInsuranceRate.fromCommandToDomain());
	}
}
