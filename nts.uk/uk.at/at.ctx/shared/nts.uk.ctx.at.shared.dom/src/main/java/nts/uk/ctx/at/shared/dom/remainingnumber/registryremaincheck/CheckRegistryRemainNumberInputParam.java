package nts.uk.ctx.at.shared.dom.remainingnumber.registryremaincheck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CheckRegistryRemainNumberInputParam {
	/**	会社ID */
	private String cid;
	/**	社員ID */
	private String sid;
	/**	集計開始日, 集計終了日 */
	private DatePeriod baseDateData;
	/**	モード: True: , False:  */
	private boolean mode;
	/**	基準日 */
	private GeneralDate baseDate;
	/**	登録期間の開始日 , 登録期間の終了日*/
	private DatePeriod registryDateData;
	/**	登録対象区分: True: 申請、False: 日別実績か */
	private boolean registryAtr;
	/**登録対象一覧: True -> 申請	 */
	private ApplicationData appData;
	/**登録対象一覧: False -> 	 日別実績の勤務情報*/
	private DailyWorkData dailyData;
	/**	代休チェック区分: True:  , False*/
	private boolean daikyuAtr;
	/**	振休チェック区分 */
	private boolean furikyuAtr;
	/**	年休チェック区分 */
	private boolean nenkyuAtr;
	/**	積休チェック区分 */
	private boolean sekikyuAtr;
	/**	特休チェック区分 */
	private boolean tokkyuAtr;
	/**公休チェック区分	 */
	private boolean koukyuAtr;
	/**	超休チェック区分 */
	private boolean choukyuAtr;
}
