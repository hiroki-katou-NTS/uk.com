package nts.uk.ctx.at.request.app.command.applicationreason;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateApplicationReasonCommand {
	List<ApplicationReasonCommand> listCommand;
}
