package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;

/**
 * 特別休暇暫定データ
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterimSpecialHolidayMng extends AggregateRoot{
	/**	社員ID */
	private String sid;
	/**	年月日 */
	private GeneralDate ymd;
	/**	特別休暇コード */
	private int specialHolidayCode;
	/**	予定実績区分 */
	private ScheduleRecordAtr scheRecordAtr;
	/**時間特休使用 */
	private UseTime useTimes;
	/**	特休使用 */
	private UseDay useDays;
}
