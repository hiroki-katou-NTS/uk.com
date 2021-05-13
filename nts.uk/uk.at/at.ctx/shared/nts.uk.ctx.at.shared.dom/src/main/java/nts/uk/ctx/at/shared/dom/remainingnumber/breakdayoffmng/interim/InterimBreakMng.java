package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定休出管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterimBreakMng extends InterimRemain implements InterimMngCommon{
	/**	１日相当時間 */
	private AttendanceTime onedayTime;
	/**	使用期限日 */
	private GeneralDate expirationDate;
	/**	発生時間数 */
	private OccurrenceTime occurrenceTimes;
	/**	発生日数 */
	private OccurrenceDay occurrenceDays;
	/**	半日相当時間 */
	private AttendanceTime haftDayTime;
	/**	未使用時間数 */
	private UnUsedTime unUsedTimes;
	/**	未使用日数 */
	private UnUsedDay unUsedDays;

	@Override
	public String getId() {
		return this.getRemainManaID();
	}

	public InterimBreakMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, AttendanceTime onedayTime, GeneralDate expirationDate,
			OccurrenceTime occurrenceTimes, OccurrenceDay occurrenceDays, AttendanceTime haftDayTime,
			UnUsedTime unUsedTimes, UnUsedDay unUsedDays) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.onedayTime = onedayTime;
		this.expirationDate = expirationDate;
		this.occurrenceTimes = occurrenceTimes;
		this.occurrenceDays = occurrenceDays;
		this.haftDayTime = haftDayTime;
		this.unUsedTimes = unUsedTimes;
		this.unUsedDays = unUsedDays;
	}

}
