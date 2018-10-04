package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;
/**
 * 残数作成元情報(予定)
 * @author do_dt
 *
 */

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ScheRemainCreateInfor {
	/**	社員ID */
	private String sid;
	/**年月日	 */
	private GeneralDate ymd;
	/**	勤務種類コード */
	private String workTypeCode;
	/**	休暇の扱い*/
	private Optional<TreatmentOfVacation> treatmentVacation;
	/**	就業時間帯コード */
	private Optional<String> workTimeCode;
	/**	振休として扱う : True: する、False：　しない */
	private boolean pauseAsTreat;
	/**
	 * 確定区分
	 */
	private boolean confirmedAtr;
}
