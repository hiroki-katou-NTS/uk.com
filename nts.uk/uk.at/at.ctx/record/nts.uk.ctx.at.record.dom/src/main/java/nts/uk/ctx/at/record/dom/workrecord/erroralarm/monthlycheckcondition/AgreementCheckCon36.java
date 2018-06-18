package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;

/**
 * 36協定チェック条件
 * @author tutk
 *
 */
@Getter
public class AgreementCheckCon36 extends AggregateRoot {
	/**ID*/
	private String errorAlarmCheckID;
	/**区分*/
	private ErrorAlarmRecord classification;
	/**比較演算子*/
	private SingleValueCompareType compareOperator;
	/**36協定エラーアラーム前時間*/
	private BigDecimal eralBeforeTime;

	public AgreementCheckCon36(String errorAlarmCheckID, ErrorAlarmRecord classification, SingleValueCompareType compareOperator, BigDecimal eralBeforeTime) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.classification = classification;
		this.compareOperator = compareOperator;
		this.eralBeforeTime = eralBeforeTime;
	}
	
	
}
