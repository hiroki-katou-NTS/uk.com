package nts.uk.ctx.bs.employee.dom.employment.stampcard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author sonnlb 打刻カード
 */
@AllArgsConstructor
@NoArgsConstructor
public class StampCard extends AggregateRoot {

	/**
	 * 個人ID
	 */
	String personId;

	/**
	 * 打刻カード番号
	 */

	CardNumber cardNumber;

	public static StampCard createFromJavaType(String personId, String cardNumber) {

		return new StampCard(personId, new CardNumber(cardNumber));

	}

}
