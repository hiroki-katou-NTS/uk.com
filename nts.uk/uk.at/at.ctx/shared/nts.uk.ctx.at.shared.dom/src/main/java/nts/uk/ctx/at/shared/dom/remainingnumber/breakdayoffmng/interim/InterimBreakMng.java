package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
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

	//[1] 休出の未相殺に変換する
	public AccumulationAbsenceDetail convertUnoffset() {
		AccumulationAbsenceDetail detail = new AccuVacationBuilder(this.getSID(),
				new CompensatoryDayoffDate(false, Optional.of(this.getYmd())), OccurrenceDigClass.OCCURRENCE,
				this.getCreatorAtr().convertToMngData(false), this.getRemainManaID())
						.numberOccurren(
								new NumberConsecuVacation(new ManagementDataRemainUnit(this.getOccurrenceDays().v()),
										Optional.of(new AttendanceTime(this.getOccurrenceTimes().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(this.unUsedDays.v()),
								Optional.of(new AttendanceTime(this.unUsedTimes.v()))))
						.build();
		return new UnbalanceVacation(this.expirationDate, determineDigest() ? DigestionAtr.USED : DigestionAtr.UNUSED,
				Optional.empty(), detail, onedayTime, haftDayTime);
	}

	// [2] 未相殺数を更新する
	public InterimBreakMng updateUnoffsetNum(AccumulationAbsenceDetail detail) {
		return new InterimBreakMng(this.getRemainManaID(), this.getSID(), this.getYmd(), this.getCreatorAtr(),
				this.getRemainType(), this.getOnedayTime(), this.getExpirationDate(), this.getOccurrenceTimes(),
				this.occurrenceDays, this.haftDayTime,
				new UnUsedTime(detail.getUnbalanceNumber().getTime().map(x -> x.v()).orElse(0)),
				new UnUsedDay(detail.getUnbalanceNumber().getDay().v()));
	}

	// [1] 消化済みかどうか判断する
	private boolean determineDigest() {
		return this.unUsedDays.v() <= 0 && this.unUsedTimes.v() <= 0;
	}
}
