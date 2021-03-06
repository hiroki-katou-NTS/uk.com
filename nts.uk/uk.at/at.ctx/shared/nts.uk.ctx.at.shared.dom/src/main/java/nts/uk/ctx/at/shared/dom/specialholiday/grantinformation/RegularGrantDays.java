package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;

/**
 * 定期付与日
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegularGrantDays {

	/** 付与日数 */
	private GrantedDays grantDays;

	/**
	 * createFromJavaType
	 * @param grantDays
	 * @return
	 */
	public static RegularGrantDays createFromJavaType(int grantDays) {
		return new RegularGrantDays(new GrantedDays(grantDays));
	}

	static public RegularGrantDays of(
			/** 付与日数 */
			GrantedDays grantDays
		){
			RegularGrantDays c = new RegularGrantDays();
			/** 付与日数 */
			c.grantDays=grantDays;

			return c;
		}

}
