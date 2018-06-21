package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.VacationTimeInfor;

/**
 * 残数作成元情報(実績)
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecordRemainCreateInfor {
	/**	社員ID */
	private String sid;
	/**	年月日 */
	private GeneralDate ymd;
	/**	勤務種類コード */
	private String workTypeCode;
	/**	時間休暇使用情報 */
	private List<VacationTimeInfor> lstVacationTimeInfor;
	/**	振替残業時間合計 */
	private Integer transferOvertimesTotal;
	/**	振替時間合計 */
	private Integer transferTotal;
	/**	就業時間帯コード */
	private Optional<String> workTimeCode;
}
