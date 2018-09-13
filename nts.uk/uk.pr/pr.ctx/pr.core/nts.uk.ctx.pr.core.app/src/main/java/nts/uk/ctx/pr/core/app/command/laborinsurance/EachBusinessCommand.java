package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusinessName;

import java.util.Optional;

@Value
public class EachBusinessCommand {
    private int occAccInsurBusNo;
    private int toUse;
    private String name;
}
