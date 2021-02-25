package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalItemDto {
	
	private Integer itemNo;
	
	private String itemName;
	// 属性
	private OptionalItemAtr optionalItemAtr;
}
