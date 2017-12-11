package nts.uk.ctx.pereg.dom.person.info.order;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PerInfoCtgOrder extends AggregateRoot {
	private String personInfoCtgId;
	private String companyId;
	private DispOrder dispOrder;

	private PerInfoCtgOrder(String personInfoCtgId, String companyId, int dispOrder) {
		super();
		this.personInfoCtgId = personInfoCtgId;
		this.companyId = companyId;
		this.dispOrder = new DispOrder(dispOrder);
	}

	public static PerInfoCtgOrder createFromJavaType(String personInfoCtgId, String companyId, int dispOrder) {
		return new PerInfoCtgOrder(personInfoCtgId, companyId, dispOrder);
	}

}
