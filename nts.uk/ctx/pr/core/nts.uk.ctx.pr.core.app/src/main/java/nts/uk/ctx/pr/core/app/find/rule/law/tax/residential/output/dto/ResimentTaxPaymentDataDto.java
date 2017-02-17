package nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.output.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResimentTaxPaymentDataDto {
	private String resimentTaxCode;
	
	private int yearMonth;
	
	private BigDecimal taxPayRollMoney;
	
	private BigDecimal taxBonusMoney;
	
	private BigDecimal taxOverDueMoney;
	
	private BigDecimal taxDemandChargeMoyney;
	
	private String address;
	
	private LocalDate dueDate;
	
	private int headcount;
	
	private BigDecimal retirementBonusAmout;
	
	private BigDecimal cityTaxMoney;
	
	private BigDecimal prefectureTaxMoney;
}
