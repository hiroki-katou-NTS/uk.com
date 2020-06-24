package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;

/**
 * 休出代休明細
 * 
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakDayOffDetail {
	/** 社員ID */
	private String sid;
	/** 状態 */
	private MngDataStatus dataAtr;
	/** 年月日 */
	private CompensatoryDayoffDate ymdData;
	/** 発生消化区分 */
	private OccurrenceDigClass occurrentClass;
	/** 休出の未使用 */
	private Optional<UnUserOfBreak> unUserOfBreak;
	/** 代休の未相殺 */
	private Optional<UnOffSetOfDayOff> unOffsetOfDayoff;

	public AccumulationAbsenceDetail convert() {
		return new AccuVacationBuilder(sid, ymdData, occurrentClass, dataAtr, "")
				.numberOccurren(new NumberConsecuVacation()).unbalanceNumber(new NumberConsecuVacation())
				.unbalanceVacation(
						unUserOfBreak.isPresent() ? new UnbalanceVacation(unUserOfBreak.get().getExpirationDate(),
								unUserOfBreak.get().getDigestionAtr(), unUserOfBreak.get().getDisappearanceDate(),
								new AttendanceTime(unUserOfBreak.get().getOnedayTime()),
								new AttendanceTime(unUserOfBreak.get().getHaftDayTime())) : null)
				.build();
	}
}
