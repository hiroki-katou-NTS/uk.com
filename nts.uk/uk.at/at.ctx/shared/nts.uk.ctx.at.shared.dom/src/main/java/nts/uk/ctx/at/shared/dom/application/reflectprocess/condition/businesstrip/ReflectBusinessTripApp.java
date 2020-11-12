package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ApplicationReflect;

/**
 * @author thanh_nx
 *
 *         出張申請の反映
 */
@Getter
public class ReflectBusinessTripApp implements DomainAggregate, ApplicationReflect {

	// 会社ID
	private String companyId;

	public ReflectBusinessTripApp(String companyId) {
		super();
		this.companyId = companyId;
	}

}
