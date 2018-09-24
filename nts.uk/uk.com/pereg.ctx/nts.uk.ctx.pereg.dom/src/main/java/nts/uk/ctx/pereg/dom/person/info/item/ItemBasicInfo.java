package nts.uk.ctx.pereg.dom.person.info.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemBasicInfo {
	private String itemCode;
	
	private String itemName;
	
	private String itemId;
	
	private int requiredAtr;
}
