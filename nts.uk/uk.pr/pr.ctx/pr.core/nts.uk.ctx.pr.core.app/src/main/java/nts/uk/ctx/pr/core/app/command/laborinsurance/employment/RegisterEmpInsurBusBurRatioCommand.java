package nts.uk.ctx.pr.core.app.command.laborinsurance.employment;

import java.util.List;

import lombok.Value;

@Value
public class RegisterEmpInsurBusBurRatioCommand {
	
    private String hisId;
    private List<EmpInsurBusBurRatioCommand> listEmpInsurPreRate;
    private Integer endYearMonth;
    private Integer startYearMonth;
    private boolean isNewMode;

}
