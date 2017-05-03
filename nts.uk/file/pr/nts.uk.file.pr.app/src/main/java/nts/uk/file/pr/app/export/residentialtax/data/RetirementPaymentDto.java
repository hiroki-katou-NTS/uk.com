package nts.uk.file.pr.app.export.residentialtax.data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class RetirementPaymentDto {
	private String personId;
    private GeneralDate payDate;
    private int trialPeriodSet;
    private int exclusionYears;
    private int additionalBoardYears;
    private int boardYears;
    private BigDecimal totalPaymentMny;
    private BigDecimal deduction1Mny;
    private BigDecimal deduction2Mny;
    private BigDecimal deduction3Mny;
    private int otherRetirementPayOp;
    private int taxCalMethodSet;
    private BigDecimal incomeTaxMny;
    private BigDecimal cityTaxMny;
    private BigDecimal prefectureTaxMny;
    private BigDecimal totalDeductionMny;
    private BigDecimal actualRecieveMny;
    private String  withholdingMeno;
    
}
