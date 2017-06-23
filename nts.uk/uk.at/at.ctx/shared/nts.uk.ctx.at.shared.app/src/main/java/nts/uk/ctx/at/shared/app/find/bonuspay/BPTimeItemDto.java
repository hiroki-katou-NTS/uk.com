package nts.uk.ctx.at.shared.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemDto {
	public String companyId;
	private String timeItemId;
	public int useAtr;
	public String timeItemName;
	public int id;
	public int timeItemTypeAtr;
}
