package nts.uk.ctx.at.record.dom.stamp.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.stamp.CardNumber;

@AllArgsConstructor
@Getter
public class StampCardItem extends AggregateRoot {
	private String employeeID;
	private CardNumber cardNumber;

	public static StampCardItem createFromJavaType(String employeeID, String cardNumber) {
		return new StampCardItem(employeeID, new CardNumber(cardNumber));
	}
}
