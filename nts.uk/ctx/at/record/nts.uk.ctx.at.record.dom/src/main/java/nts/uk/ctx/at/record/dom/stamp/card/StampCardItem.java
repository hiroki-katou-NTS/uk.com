package nts.uk.ctx.at.record.dom.stamp.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.stamp.CardNumber;

@AllArgsConstructor
@Getter
public class StampCardItem extends AggregateRoot {
	private String companyId;
	private CardNumber cardNumber;
	private String personId;

	public static StampCardItem createFromJavaType(String companyId, String cardNumber, String personId) {
		return new StampCardItem(companyId, new CardNumber(cardNumber), personId);
	}

}
