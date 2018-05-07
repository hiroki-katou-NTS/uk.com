package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class StampCard extends AggregateRoot {
	
	// domain name: 打刻カード
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 番号
	 */
	private StampNumber stampNumber;
	
	/**
	 * 登録日付
	 */
	private GeneralDate registerDate;

}
