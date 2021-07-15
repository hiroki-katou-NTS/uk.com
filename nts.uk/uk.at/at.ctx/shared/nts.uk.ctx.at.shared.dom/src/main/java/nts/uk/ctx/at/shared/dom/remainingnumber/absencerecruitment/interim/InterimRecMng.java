package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振出管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimRecMng extends InterimRemain implements InterimMngCommon {

	/**	使用期限日 */
	private GeneralDate expirationDate;
	/**	発生日数 */
	private OccurrenceDay occurrenceDays;
	/**	未使用日数 */
	private UnUsedDay unUsedDays;
	@Override
	public String getId() {
		return this.getRemainManaID();
	}
	public InterimRecMng(String remainManaID, String sid, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType,
			GeneralDate useDate, OccurrenceDay occurrenceDay, UnUsedDay unUsedDay) {
		super(remainManaID, sid, ymd, creatorAtr, remainType);

		this.expirationDate = useDate;
		this.occurrenceDays = occurrenceDay;
		this.unUsedDays = unUsedDay;
	}

	//[1] 振出の未相殺に変換する
	public AccumulationAbsenceDetail convertUnoffset() {
		AccumulationAbsenceDetail detail = new AccuVacationBuilder(this.getSID(),
				new CompensatoryDayoffDate(false, Optional.of(this.getYmd())), OccurrenceDigClass.OCCURRENCE,
				this.getCreatorAtr().convertToMngData(false), this.getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(this.getOccurrenceDays().v()), Optional.empty()))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(this.unUsedDays.v()), Optional.empty()))
						.build();
		return new UnbalanceCompensation(detail, this.getExpirationDate(),
				determineDigest() ? DigestionAtr.USED : DigestionAtr.UNUSED, Optional.empty(),
				HolidayAtr.STATUTORY_HOLIDAYS);
	}
	
	//[2] 未相殺数を更新する
	public InterimRecMng updateUnoffsetNum(AccumulationAbsenceDetail detail) {
		return new InterimRecMng(this.getRemainManaID(), this.getSID(), this.getYmd(), this.getCreatorAtr(),
				this.getRemainType(), this.expirationDate, this.occurrenceDays,
				new UnUsedDay(detail.getUnbalanceNumber().getDay().v()));
	}
	
	//[1] 消化済みかどうか判断する
	private boolean determineDigest() {
		return this.unUsedDays.v() <= 0;
	}
	

}
