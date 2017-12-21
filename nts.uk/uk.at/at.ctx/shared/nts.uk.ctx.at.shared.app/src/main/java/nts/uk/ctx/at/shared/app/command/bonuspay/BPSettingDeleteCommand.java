package nts.uk.ctx.at.record.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPSettingDeleteCommand {
	public String code;
	public String name;
}
