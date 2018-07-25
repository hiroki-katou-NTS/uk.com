package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder {
	private String itemId;
	private String ctgId;
	private String itemCode;
	private String parentCode;
	private int disOrder;
	private int displayOrder;

}
