package nts.uk.ctx.pereg.app.command.copysetting.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerInfoDefDto {
	private String id;
	private String itemCd;
	private String itemName;
	private String perCtgId;
	private String alreadyItemDefCopy;
	private String itemParentCd;
	private Boolean checked;
}
