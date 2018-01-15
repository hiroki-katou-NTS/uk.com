/**
 * 11:38:35 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;

/**
 * @author hungnm
 *
 */
//勤務種類の条件
@Getter
public class WorkTypeCondition extends DomainObject {
	
	//勤務種類の条件を使用する
	private Boolean useAtr;
	
	//予実比較による絞り込み方法
	private FilterByCompare comparePlanAndActual;

	/* Constructor */
	protected WorkTypeCondition(Boolean useAtr, FilterByCompare comparePlanAndActual) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
	}

}
