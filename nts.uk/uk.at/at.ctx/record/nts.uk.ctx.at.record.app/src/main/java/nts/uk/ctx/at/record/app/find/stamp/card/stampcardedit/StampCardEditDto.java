package nts.uk.ctx.at.record.app.find.stamp.card.stampcardedit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampCardEditDto {


	private int digitsNumber;

	private int method;

	public static StampCardEditDto createFromDomain(StampCardEditing domain) {
		return new StampCardEditDto(domain.getDigitsNumber().v(), domain.getMethod().value);
	}

}
