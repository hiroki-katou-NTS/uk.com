package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemAddCommand {
	public String companyId;
	private String timeItemId;
	public int useAtr;
	public String timeItemName;
	public int id;
	public int timeItemTypeAtr;
}
