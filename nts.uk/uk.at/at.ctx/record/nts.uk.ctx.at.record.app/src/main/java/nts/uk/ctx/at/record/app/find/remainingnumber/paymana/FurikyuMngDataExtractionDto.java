package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FurikyuMngDataExtractionDto {
		
	private List<CompositePayOutSubMngData> compositePayOutSubMngData;
	private int expirationDate;
	private Double numberOfDayLeft;
	private Integer closureID;
}
