package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ApplicationReflect;

/**
 * @author thanh_nx
 *
 *         直行直帰申請の反映
 */
@Getter
public class ReflectGoBackDirectly implements DomainAggregate, ApplicationReflect {

	// 会社ID
	private String companyId;

	// 勤務情報を反映する
	private ApplicationStatusShare reflectWorkInfo;

	public ReflectGoBackDirectly(String companyId, ApplicationStatusShare reflectWorkInfo) {
		super();
		this.companyId = companyId;
		this.reflectWorkInfo = reflectWorkInfo;
	}

}
