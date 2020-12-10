/**
 * 
 */
package nts.uk.screen.at.app.kcp015;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KCP015Dto {
	
	// 年休の使用区分
	public Boolean clsOfAnnualHoliday;
	// 積立年休使用区分
	public Boolean divisionOfAnnualHoliday;
	// 60H超休使用区分
	public Boolean overtimeUseCls60H;
	// 振休使用区分
	public Boolean dvisionOfZhenxiuUse;
	// 代休使用区分
	public Boolean subLeaveUseDivision
;
	
	
	
}
