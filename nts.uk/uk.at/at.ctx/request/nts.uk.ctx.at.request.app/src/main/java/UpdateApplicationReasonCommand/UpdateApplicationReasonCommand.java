package UpdateApplicationReasonCommand;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.applicationreason.ApplicationReasonCommand;

@Data
@AllArgsConstructor
public class UpdateApplicationReasonCommand {
	List<ApplicationReasonCommand> listCommand;
}
