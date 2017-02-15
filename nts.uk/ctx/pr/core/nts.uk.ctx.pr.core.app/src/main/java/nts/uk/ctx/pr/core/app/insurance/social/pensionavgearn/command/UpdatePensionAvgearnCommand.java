package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePensionAvgearnCommand {
	List<PensionAvgearnBaseCommand> listPensionAvgearn;
}
