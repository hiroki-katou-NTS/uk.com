package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EarchInterimRemainCheck {
	/**	・代休不足区分 */
	private boolean chkSubHoliday;
	/**	・振休不足区分 */
	private boolean chkPause;
	/**	・年休不足区分 */
	private boolean chkAnnual;
	/**	・積休不足区分 */
	private boolean chkFundingAnnual;
	/**	・特休不足区分 */
	private boolean chkSpecial;
	/**	・公休不足区分 */
	private boolean chkPublicHoliday;
	/**	・超休不足区分 */
	private boolean chkSuperBreak;
}
