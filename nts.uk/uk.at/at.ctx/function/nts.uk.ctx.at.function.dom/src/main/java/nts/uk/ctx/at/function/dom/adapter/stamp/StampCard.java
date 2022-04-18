package nts.uk.ctx.at.function.dom.adapter.stamp;

import lombok.Value;

/**
 * @author ThanhNX
 * 
 *         打刻カード
 */
@Value
public class StampCard {

	/**
	 * 打刻カードID
	 */
	private String stampCardId;

	/**
	 * 社員ID
	 */
	private String employeeId;

	public StampCard(String stampCardId, String employeeId) {
		super();
		this.stampCardId = stampCardId;
		this.employeeId = employeeId;
	}

}
