/**
 * 11:38:35 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;

/**
 * @author hungnm
 *
 */
@Getter
public class WorkTypeCondition extends DomainObject {
	
	private Boolean useAtr;
	
	private FilterByCompare comparePlanAndActual;

}
