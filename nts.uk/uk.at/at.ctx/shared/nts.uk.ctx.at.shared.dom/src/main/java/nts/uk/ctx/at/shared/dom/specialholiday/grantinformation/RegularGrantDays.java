package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;

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

}
