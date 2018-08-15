package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 固定付与日
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FixGrantDate {
	/** 周期*/
	private GrantedYears interval;
	
	/** 固定付与日数 */
	private GrantedDays grantDays;

	public static FixGrantDate createFromJavaType(int interval, int grantDays) {
		return new FixGrantDate(new GrantedYears(interval), new GrantedDays(grantDays));
	}
}
