package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InterimRemainCheckInputParam {
	/**	会社ID */
	private String cid;
	/**	・社員ID */
	private String sid;
	/**	・集計開始日・集計終了日 */
	private DatePeriod datePeriod;
	/**	・モード:  True: 月次か, false: その他か */
	private boolean mode;
	/**	・基準日 */
	private GeneralDate baseDate;
	/**	・登録期間の開始日・登録期間の終了日 */
	private DatePeriod registerDate;
	/**	・登録対象区分: True: 申請 , False: 日別実績 */
	private boolean chkRegister;
	/**	・登録対象一覧 : 実績*/
	private List<RecordRemainCreateInfor> recordData;
	/**	・登録対象一覧 : 予定(List)  */
	private List<ScheRemainCreateInfor> scheData;
	/** ・登録対象一覧 :	申請(List) */
	private List<AppRemainCreateInfor> appData;
	/** 勤務種類コード(List) */
	private List<String> workTypeCds;
	/** 時間消化(Optional） */
	private Optional<TimeDigestionParam> timeDigestionUsageInfor;
//	/**	・代休チェック区分 */
//	private boolean chkSubHoliday;
//	/**	・振休チェック区分 */
//	private boolean chkPause;
//	/**	・年休チェック区分 */
//	private boolean chkAnnual;
//	/**	・積休チェック区分 */
//	private boolean chkFundingAnnual;
//	/**	・特休チェック区分 */
//	private boolean chkSpecial;
//	/**	・公休チェック区分 */
//	private boolean chkPublicHoliday;
//	/**	・超休チェック区分 */
//	private boolean chkSuperBreak;
//	/** 子の看護チェック区分 */
//	private boolean chkChildNursing;
//	/** 介護チェック区分 */
//	private boolean chkLongTermCare;
}
