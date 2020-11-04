package nts.uk.ctx.at.shared.app.workrule.workinghours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * «Temporary» 時間帯に含まれているか
 * @author tutk
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IsInculededTimeZone {
	
	/**
	 * 含まれているか
	 */
	private boolean inculedeCheck;
	
	/**
	 * 時間帯
	 */
	private TimeZoneDto timeZone;

}
