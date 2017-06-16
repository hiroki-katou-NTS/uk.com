package nts.uk.ctx.at.record.dom.stamp.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.stamp.CardNumber;

@AllArgsConstructor
@Getter
public class StampCardItem extends AggregateRoot {
	private String personId;
	private CardNumber cardNumber;

	public static StampCardItem createFromJavaType(String personId, String cardNumber) {
		return new StampCardItem(personId, new CardNumber(cardNumber));
	}
}
