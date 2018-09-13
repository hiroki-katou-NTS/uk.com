package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.NameOfEachBusiness;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusinessName;

import java.util.List;
import java.util.Optional;

@Value
public class UpdateNameOfEachBusinessCommand {
    List<EachBusinessCommand> listEachBusiness;
}
