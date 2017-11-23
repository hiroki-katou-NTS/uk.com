/**
 * 11:38:58 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;

/**
 * @author hungnm
 *
 */
// 就業時間帯の条件
@Getter
public class WorkTimeCondition extends DomainObject {

	// 勤務種類の条件を使用する
	private Boolean useAtr;

	// 予実比較による絞り込み方法
	private FilterByCompare comparePlanAndActual;

	/* Constructor */
	protected WorkTimeCondition(Boolean useAtr, FilterByCompare comparePlanAndActual) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
	}

}
