package nts.uk.ctx.pereg.dom.person.info.item;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
public class PersonInfoItemDefinitionSimple extends AggregateRoot {
	public PersonInfoItemDefinitionSimple(String itemCode, String itemName) {
		super();
		this.itemCode = new ItemCode(itemCode);
		this.itemName = new ItemName(itemName);
	}

	private ItemCode itemCode;
	private ItemName itemName;
}
