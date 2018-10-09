package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;

@Value
public class EachBusinessCommand {
    private int occAccInsurBusNo;
    private int toUse;
    private String name;
}
