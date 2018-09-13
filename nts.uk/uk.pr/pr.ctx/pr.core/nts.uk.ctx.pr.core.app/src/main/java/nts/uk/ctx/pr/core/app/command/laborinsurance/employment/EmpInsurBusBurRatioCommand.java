package nts.uk.ctx.pr.core.app.command.laborinsurance.employment;

import lombok.Value;

@Value
public class EmpInsurBusBurRatioCommand {
    
    private String hisId;
    private int empPreRateId;
    private Long indBdRatio;
    private Long empContrRatio;
    private int perFracClass;
    private int busiOwFracClass;

}
