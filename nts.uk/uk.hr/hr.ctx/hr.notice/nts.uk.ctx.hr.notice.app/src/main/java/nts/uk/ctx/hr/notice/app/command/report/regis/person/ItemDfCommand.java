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
	String categoryId;
	String categoryCode;
	String categoryName;
	int ctgType;
	String layoutItemType;
	int layoutDisOrder;
	int dispOrder;
	String itemDefId;
	String itemCode;
	String itemName;
}
