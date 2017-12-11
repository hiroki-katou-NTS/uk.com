package nts.uk.ctx.pereg.app.command.person.setting.init;

import lombok.Value;

@Value
public class InsertInitValueSettingCommand {
	private String itemCode;
	private String itemName;
}
