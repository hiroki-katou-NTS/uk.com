package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPSettingDeleteCommand {
	public String code;
	public String name;
}
