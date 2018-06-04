package nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedTime;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定休出管理データ
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimBreakMng extends AggregateRoot{
	/**	暫定休出管理データID */
	private String breakMngId;
	/**	１日相当時間 */
	private AttendanceTime onedayTime;
	/**	使用期限日 */
	private GeneralDate expirationDays;
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

}
