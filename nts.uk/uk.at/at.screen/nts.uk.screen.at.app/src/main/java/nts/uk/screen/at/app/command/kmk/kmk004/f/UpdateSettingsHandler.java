package nts.uk.screen.at.app.command.kmk.kmk004.f;

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
public class UpdateSettingsHandler {

	public int daily;
	public int weekly;

	public boolean deforWorkSurchargeWeekMonth;
	public boolean deforWorkLegalOverTimeWork;
	public boolean deforWorkLegalHoliday;
	public boolean outsideSurchargeWeekMonth;
	public boolean outsidedeforWorkLegalOverTimeWork;
	public boolean outsidedeforWorkLegalHoliday;

}
