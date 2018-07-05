package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 付与情報
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantRegular extends DomainObject {

	/* 会社ID */
	private String companyId;
}
