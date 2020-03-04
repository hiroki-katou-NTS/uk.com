/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.Getter;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@Setter
@Getter
public class ItemEndDate {
	
	String itemCode;
	String itemName;
	String itemDfId;
	
	public ItemEndDate() {
		super();
	}
	
	public ItemEndDate(String itemCode, String itemName, String itemDfId) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemDfId = itemDfId;
	}
	

}
