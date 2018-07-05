package nts.uk.ctx.at.shared.dom.specialholidaynew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 特別休暇利用条件
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SpecialLeaveRestriction extends DomainObject {
	/** 会社ID */
	private String companyId;
}
