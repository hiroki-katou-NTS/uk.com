package nts.uk.ctx.at.shared.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class BPTimeItemUpdateCommand {
	private String timeItemId;
	public int useAtr;
	public String timeItemName;
	public int timeItemNo;
	public int timeItemTypeAtr;

}
