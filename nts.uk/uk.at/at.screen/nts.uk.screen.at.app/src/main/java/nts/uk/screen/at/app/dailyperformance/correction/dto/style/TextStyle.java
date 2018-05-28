package nts.uk.screen.at.app.dailyperformance.correction.dto.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextStyle {
	
	private String rowId;
	
	private String columnKey;
	
	private String style;
}
