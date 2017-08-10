package nts.uk.ctx.bs.person.dom.person.info.order;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PerInfoItemDefOrder extends AggregateRoot {
	private String perInfoItemDefId;
	private String perInfoCtgId;
	private DispOrder dispOrder;

	private PerInfoItemDefOrder(String perInfoItemDefId, String perInfoCtgId, int dispOrder) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCtgId = perInfoCtgId;
		this.dispOrder = new DispOrder(dispOrder);
	}

	public static PerInfoItemDefOrder createFromJavaType(String perInfoItemDefId, String perInfoCtgId, int dispOrder) {
		return new PerInfoItemDefOrder(perInfoItemDefId, perInfoCtgId, dispOrder);
	}

}
