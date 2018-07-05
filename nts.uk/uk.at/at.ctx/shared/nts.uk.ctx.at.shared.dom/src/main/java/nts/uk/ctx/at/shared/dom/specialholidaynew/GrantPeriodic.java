package nts.uk.ctx.at.shared.dom.specialholidaynew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 期限情報
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantPeriodic extends DomainObject {

	/* 会社ID */
	private String companyId;
}
