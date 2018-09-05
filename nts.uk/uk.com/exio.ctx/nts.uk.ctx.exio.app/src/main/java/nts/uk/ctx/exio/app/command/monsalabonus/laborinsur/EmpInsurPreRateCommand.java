package nts.uk.ctx.exio.app.command.monsalabonus.laborinsur;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class EmpInsurPreRateCommand {
    
    private String hisId;
    private int empPreRateId;
    private BigDecimal indBdRatio;
    private BigDecimal empContrRatio;
    private int perFracClass;
    private int busiOwFracClass;

}
