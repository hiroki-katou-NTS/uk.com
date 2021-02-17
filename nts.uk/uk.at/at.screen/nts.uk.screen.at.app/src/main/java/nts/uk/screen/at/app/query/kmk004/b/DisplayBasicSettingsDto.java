package nts.uk.screen.at.app.query.kmk004.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayBasicSettingsDto {

	// 週単位
	private int weekly;
	
	// 日単位
	private int daily;
	
	private boolean deforWorkSurchargeWeekMonth;
	
	private boolean deforWorkLegalOverTimeWork;
	
	private boolean deforWorkLegalHoliday;
	
	private boolean outsideSurchargeWeekMonth;
	
	private boolean outsidedeforWorkLegalOverTimeWork;
	
	private boolean outsidedeforWorkLegalHoliday;
	
}
