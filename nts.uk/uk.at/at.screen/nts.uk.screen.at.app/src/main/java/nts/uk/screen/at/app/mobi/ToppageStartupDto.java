/**
 * 
 */
package nts.uk.screen.at.app.mobi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetInfoDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHoursDto;

/**
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ToppageStartupDto {
	 
	public DisplayNotifiDto displayNotifiDto;
	public ToppageOptionalWidgetInfoDto ktg029;
	public ToppageOvertimeHoursDto overtimeHoursDto;
}
