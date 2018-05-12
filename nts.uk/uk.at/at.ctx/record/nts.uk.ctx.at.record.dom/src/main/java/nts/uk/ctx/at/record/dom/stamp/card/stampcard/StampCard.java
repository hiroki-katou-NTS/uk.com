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
	 * 打刻カードID
	 */
	private String stampCardId;

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

	public static StampCard createFromJavaType(String stampCardId, String employeeId, String stampNumber,
			GeneralDate registerDate) {
		return new StampCard(stampCardId, employeeId, new StampNumber(stampNumber), registerDate);
	}

	public StampCard(String stampCardId, String employeeId, StampNumber stampNumber, GeneralDate registerDate) {
		super();
		this.stampCardId = stampCardId;
		this.employeeId = employeeId;
		this.stampNumber = stampNumber;
		this.registerDate = registerDate;
	}
}
