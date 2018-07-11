package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

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
	private int interval;
	
	/** 固定付与日数 */
	private GrantedDays grantDays;

	public static FixGrantDate createFromJavaType(int interval, GrantedDays grantDays) {
		return new FixGrantDate(interval, grantDays);
	}
}
