package command.person.setting.init.category;

import lombok.Value;

@Value
public class CopyInitValueSetCtgCommand {

	private String idSource;
	private String codeInput;
	private String nameInput;
	private boolean overWrite;
}
