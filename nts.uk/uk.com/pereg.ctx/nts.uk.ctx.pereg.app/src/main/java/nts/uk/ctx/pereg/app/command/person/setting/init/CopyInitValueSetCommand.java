package nts.uk.ctx.pereg.app.command.person.setting.init;

import lombok.Value;

@Value
public class CopyInitValueSetCommand {

	private String idSource;
	private String codeInput;
	private String nameInput;
	private boolean overWrite;
}
