package nts.uk.ctx.pereg.dom.person.info.order;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PerInfoItemDefOrder extends AggregateRoot {
	private String perInfoItemDefId;
	private String perInfoCtgId;
	private DispOrder dispOrder;
	private DispOrder displayOrder;

	private PerInfoItemDefOrder(String perInfoItemDefId, String perInfoCtgId, int dispOrder, int displayOrder) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCtgId = perInfoCtgId;
		this.dispOrder = new DispOrder(dispOrder);
		this.displayOrder = new DispOrder(displayOrder);
	}

	public static PerInfoItemDefOrder createFromJavaType(String perInfoItemDefId, String perInfoCtgId, int dispOrder,
			int displayOrder) {
		return new PerInfoItemDefOrder(perInfoItemDefId, perInfoCtgId, dispOrder, displayOrder);
	}

}
