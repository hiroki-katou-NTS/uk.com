package nts.uk.ctx.pereg.dom.person.info.order;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PerInfoItemDefOrder extends AggregateRoot {
	private String perInfoItemDefId;
	private String perInfoCtgId;
	private DispOrder dispOrder;
	private DispOrder displayOrder;

	public PerInfoItemDefOrder(String perInfoItemDefId, String perInfoCtgId, int dispOrder, int displayOrder) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCtgId = perInfoCtgId;
		this.dispOrder = new DispOrder(dispOrder);
		this.displayOrder = new DispOrder(displayOrder);
	}
	
	public PerInfoItemDefOrder(String perInfoItemDefId, String perInfoCtgId, int dispOrder) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCtgId = perInfoCtgId;
		this.dispOrder = new DispOrder(dispOrder);
	}

	public PerInfoItemDefOrder() {
		super();
	}
	
	/**
	 * category is not Fixed :  dong nhat display order, dis order
	 * @param perInfoItemDefId
	 * @param perInfoCtgId
	 * @param dispOrder
	 * @param displayOrder
	 * @return
	 */

	public static PerInfoItemDefOrder createFromJavaType(String perInfoItemDefId, String perInfoCtgId, int dispOrder,
			int displayOrder) {
		return new PerInfoItemDefOrder(perInfoItemDefId, perInfoCtgId, dispOrder, displayOrder);
	}
	
	/**
	 * category is Fixed :  ko update display order
	 * @param perInfoItemDefId
	 * @param perInfoCtgId
	 * @param dispOrder
	 * @return
	 */
	public static PerInfoItemDefOrder createFromJavaType(String perInfoItemDefId, String perInfoCtgId, int dispOrder) {
		return new PerInfoItemDefOrder(perInfoItemDefId, perInfoCtgId, dispOrder);
	}

}
