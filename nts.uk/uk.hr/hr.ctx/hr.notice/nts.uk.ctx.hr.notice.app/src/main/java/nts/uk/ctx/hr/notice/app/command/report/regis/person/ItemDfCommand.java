package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDfCommand {
	public String categoryId;
	public String categoryCode;
	public String categoryName;
	public int ctgType;
	public String layoutItemType;
	public int layoutDisOrder;
	public int dispOrder;
	public String itemDefId;
	public String itemCode;
	public String itemName;
}
