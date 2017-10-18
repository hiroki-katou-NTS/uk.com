package command.person.setting.init.category;

import lombok.Value;

@Value
public class CopyInitValueSetCtgCommand {

	private String id;
	private String codeNew;
	private String nameNew;
	private boolean copy;
}
