package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class AgreementCheckCon36AdapterPubDto {
	/**ID*/
	private String errorAlarmCheckID;
	/**区分*/
	private int classification;
	/**比較演算子*/
	private int compareOperator;
	/**36協定エラーアラーム前時間*/
	private BigDecimal eralBeforeTime;
	public AgreementCheckCon36AdapterPubDto(String errorAlarmCheckID, int classification, int compareOperator, BigDecimal eralBeforeTime) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.classification = classification;
		this.compareOperator = compareOperator;
		this.eralBeforeTime = eralBeforeTime;
	}
	
	
}
