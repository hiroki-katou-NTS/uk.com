package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureIdPresentClosingPeriodDto {
	
	//締めID
	private Integer closureId;
	
	//現在の締め期間
	private PresentClosingPeriodImportDto currentClosingPeriod;
	
}
