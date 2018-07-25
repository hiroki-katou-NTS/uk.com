package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
/**
 * 月別実績の固定抽出条件
 * @author tutk
 *
 */
@Getter
public class FixedExtraMon extends AggregateRoot {
	/**ID*/
	private String  monAlarmCheckID;
	/**NO*/
	private  SysFixedMonPerEral fixedExtraItemMonNo;
	/**使用区分*/
	private boolean useAtr;
	/**表示メッセージ*/
	private Optional<FixedConditionWorkRecordName> message;
	public FixedExtraMon(String monAlarmCheckID, SysFixedMonPerEral fixedExtraItemMonNo, boolean useAtr, FixedConditionWorkRecordName message) {
		super();
		this.monAlarmCheckID = monAlarmCheckID;
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.useAtr = useAtr;
		this.message = Optional.ofNullable(message);
	}
	
	
}
