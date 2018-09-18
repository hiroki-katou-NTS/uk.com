package nts.uk.ctx.pr.core.app.command.laborinsurance;

import java.util.List;

import lombok.Value;

@Value
public class UpdateNameOfEachBusinessCommand {
    List<EachBusinessCommand> listEachBusiness;
}
