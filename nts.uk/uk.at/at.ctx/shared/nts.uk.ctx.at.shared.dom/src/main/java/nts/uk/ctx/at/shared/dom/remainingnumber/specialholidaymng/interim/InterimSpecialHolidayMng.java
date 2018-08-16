package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;

/**
 * 特別休暇暫定データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterimSpecialHolidayMng extends AggregateRoot{
	/**
	 * 特別休暇暫定ID
	 */
	private String specialHolidayId;
	/**	特別休暇コード */
	private int specialHolidayCode;
	/**	予定実績区分 */
	private ManagermentAtr mngAtr;
	/**時間特休使用 */
	private Optional<UseTime> useTimes;
	/**	特休使用 */
	private Optional<UseDay> useDays;
}
