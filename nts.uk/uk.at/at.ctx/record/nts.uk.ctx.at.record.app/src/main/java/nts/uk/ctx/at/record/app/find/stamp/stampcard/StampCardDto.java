package nts.uk.ctx.at.record.app.find.stamp.stampcard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StampCardDto {
	private String cardNumber;
	private String PID;
	public StampCardDto fromDomain(StampCardItem domain) {
		return new StampCardDto(domain.getPersonId()
				,domain.getCardNumber().v());
	}
	
}
