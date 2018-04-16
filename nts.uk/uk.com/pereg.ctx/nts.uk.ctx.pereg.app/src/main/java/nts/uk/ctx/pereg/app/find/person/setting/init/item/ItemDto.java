package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
	
	// 個人情報項目定義ID
	private String perInfoItemDefId;
	
	private String itemName;
	
	private boolean disabled;
	
	private int isRequired;

}
