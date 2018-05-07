package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
/**
 * 月別実績の固定抽出項目
 * @author tutk
 *
 */
@Getter
public class FixedExtraItemMon extends AggregateRoot {
	/**NO*/
	private SysFixedMonPerEral fixedExtraItemMonNo;
	/**名称*/
	private FixConWorkRecordName fixedExtraItemMonName;
	/**初期メッセージ*/
	private FixedConditionWorkRecordName message;

	
	public FixedExtraItemMon(SysFixedMonPerEral fixedExtraItemMonNo, FixConWorkRecordName fixedExtraItemMonName, FixedConditionWorkRecordName message) {
		super();
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.fixedExtraItemMonName = fixedExtraItemMonName;
		this.message = message;
	}
	
	
}
