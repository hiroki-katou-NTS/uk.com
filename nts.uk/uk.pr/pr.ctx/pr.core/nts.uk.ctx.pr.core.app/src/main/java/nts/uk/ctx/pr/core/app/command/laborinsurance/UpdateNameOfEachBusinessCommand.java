package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;
import java.util.List;

@Value
public class UpdateNameOfEachBusinessCommand {
    List<EachBusinessCommand> listEachBusiness;
}
