/**
 * 
 */
package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	public Integer closureID;
	public Integer closureYearMonth;
	
}
