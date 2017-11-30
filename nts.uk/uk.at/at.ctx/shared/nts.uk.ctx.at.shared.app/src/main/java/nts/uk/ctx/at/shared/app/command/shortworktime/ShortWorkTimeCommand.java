/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.shortworktime;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.pereg.app.PeregItem;

/**
 * @author danpv
 *
 */
@Getter
public class ShortWorkTimeCommand {

	/**
	 * 短時間開始日
	 */
	@PeregItem("IS00102")
	private GeneralDate startDate;

	/**
	 * 短時間終了日
	 */
	@PeregItem("IS00103")
	private GeneralDate endDate;

	/**
	 * 短時間勤務区分
	 */
	@PeregItem("IS00104")
	private int childCareAtr;

	/**
	 * 育児開始1
	 */
	@PeregItem("IS00106")
	public TimeWithDayAttr startTime1;

	/**
	 * 育児終了1
	 */
	@PeregItem("IS00107")
	public TimeWithDayAttr endTime1;

	/**
	 * 育児開始2
	 */
	@PeregItem("IS00109")
	public TimeWithDayAttr startTime2;

	/**
	 * 育児終了2
	 */
	@PeregItem("IS00110")
	public TimeWithDayAttr endTime2;

	/**
	 * IS00111 11 備考
	 */

	/**
	 * IS00114 14 "家族の同一の要介護状態について介護短時間勤務をしたことがあるか
	 */

	/**
	 * IS00115 15 "対象の家族についてのこれまでの介護休業および介護短時間勤務の日数
	 */

	/**
	 * IS00116 16 "子の区分
	 */

	/**
	 * IS00117 17 "縁組成立の年月日
	 */
}
