package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.enums.DistributeSet;
import nts.uk.ctx.pr.proto.dom.enums.DistributeWay;

/** 按分設定 */
public class Distribute extends DomainObject {
	@Getter
	private DistributeWay method;
	@Getter
	private DistributeSet setting;
	public Distribute(DistributeWay method, DistributeSet setting) {
		super();
		this.method = method;
		this.setting = setting;
	}
	
}
