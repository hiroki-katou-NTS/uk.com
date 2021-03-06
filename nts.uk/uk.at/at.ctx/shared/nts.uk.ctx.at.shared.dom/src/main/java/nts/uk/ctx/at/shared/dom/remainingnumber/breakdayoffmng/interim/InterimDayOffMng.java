package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定代休管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimDayOffMng extends InterimRemain implements InterimMngCommon{
	/**	必要時間数 */
	private RequiredTime requiredTime;
	/**	必要日数 */
	private RequiredDay requiredDay;
	/**	未相殺時間数 */
	private UnOffsetTime unOffsetTimes;
	/**	未相殺日数 */
	private UnOffsetDay unOffsetDay;
	/** 時間休暇種類*/
	private Optional<DigestionHourlyTimeType> appTimeType;
	
	@Override
	public String getId() {
		return this.getRemainManaID();
	}

	public InterimDayOffMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, RequiredTime requiredTime, RequiredDay requiredDay, UnOffsetTime unOffsetTimes,
			UnOffsetDay unOffsetDay, Optional<DigestionHourlyTimeType> appTimeType) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.requiredTime = requiredTime;
		this.requiredDay = requiredDay;
		this.unOffsetTimes = unOffsetTimes;
		this.unOffsetDay = unOffsetDay;
		this.appTimeType = appTimeType;
	}
	
	// [1] 逐次発生の休暇明細に変換する
	public AccumulationAbsenceDetail convertSeqVacationState() {
		return new AccuVacationBuilder(this.getSID(), new CompensatoryDayoffDate(false, Optional.of(this.getYmd())),
				OccurrenceDigClass.DIGESTION, this.getCreatorAtr().convertToMngData(false), this.getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(this.getRequiredDay().v()), Optional.of(new AttendanceTime(this.getRequiredTime().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(this.getUnOffsetDay().v()),
								Optional.of(new AttendanceTime(this.getUnOffsetTimes().v()))))
						.build();
	}

	// [2] 未相殺数を更新する
	public InterimDayOffMng updateUnoffsetNum(AccumulationAbsenceDetail detail) {
		return new InterimDayOffMng(this.getRemainManaID(), this.getSID(), this.getYmd(), this.getCreatorAtr(),
				this.getRemainType(), requiredTime, requiredDay,
				new UnOffsetTime(detail.getUnbalanceNumber().getTime().map(x -> x.v()).orElse(0)),
				new UnOffsetDay(detail.getUnbalanceNumber().getDay().v()), appTimeType);
	}
}
