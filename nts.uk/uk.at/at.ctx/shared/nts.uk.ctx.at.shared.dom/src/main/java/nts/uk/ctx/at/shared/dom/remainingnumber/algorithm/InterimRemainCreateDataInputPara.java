package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InterimRemainCreateDataInputPara {
	/**会社ID	 */
	private String cid;
	/**	社員ID */
	private String sid;
	/**期間	 */
	private DatePeriod dateData;
	/**	残数作成元情報(実績)(List)*/
	private List<RecordRemainCreateInfor> recordData;
	/**	残数作成元の勤務予定(List)  */
	private List<ScheRemainCreateInfor> scheData;
	/**	残数作成元の申請(List) */
	private List<AppRemainCreateInfor> appData;
	/**	時間代休利用: True: 利用, False: 未利用 */
	private boolean dayOffTimeIsUse;
}
