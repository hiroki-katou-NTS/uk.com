package nts.uk.ctx.pr.core.app.command.laborinsurance.employment;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class EmpInsurBusBurRatioCommand {
    
    private String hisId;
    private int empPreRateId;
    private BigDecimal indBdRatio;
    private BigDecimal empContrRatio;
    private int perFracClass;
    private int busiOwFracClass;
;

}
