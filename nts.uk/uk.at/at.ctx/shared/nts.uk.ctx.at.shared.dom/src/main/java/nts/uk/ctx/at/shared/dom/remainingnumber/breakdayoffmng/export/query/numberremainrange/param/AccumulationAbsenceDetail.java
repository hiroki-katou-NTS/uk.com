package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecDetailPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnOffsetOfAbs;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnUseOfRec;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnOffSetOfDayOff;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnUserOfBreak;

/**
 * @author ThanhNX
 *
 *         逐次発生の休暇明細
 */
@Getter
public class AccumulationAbsenceDetail {

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 年月日
	 */
	private CompensatoryDayoffDate dateOccur;

	/**
	 * 発生消化区分
	 */
	private OccurrenceDigClass occurrentClass;

	/**
	 * 状態
	 */
	private MngDataStatus dataAtr;

	/**
	 * 残数管理データID
	 */
	private String manageId;

	/**
	 * 発生数
	 * 
	 * 発生
	 */
	private NumberConsecuVacation numberOccurren;

	/**
	 * 未相殺数
	 * 
	 * 未使用
	 */
	private NumberConsecuVacation unbalanceNumber;

	/**
	 * 休暇発生明細
	 */
	private Optional<UnbalanceVacation> unbalanceVacation;

	/**
	 * 振出の未相殺
	 */
	private Optional<UnbalanceCompensation> unbalanceCompensation;

	public AccumulationAbsenceDetail(AccuVacationBuilder builder) {

		this.employeeId = builder.getEmployeeId();

		this.dateOccur = builder.getDateOccur();

		this.occurrentClass = builder.getOccurrentClass();

		this.dataAtr = builder.getDataAtr();

		this.manageId = builder.getManageId();

		this.numberOccurren = builder.getNumberOccurren();

		this.unbalanceNumber = builder.getUnbalanceNumber();

		this.unbalanceVacation = builder.getUnbalanceVacation();

		this.unbalanceCompensation = builder.getUnbalanceCompensation();

	}

	public BreakDayOffDetail convertDefault() {

		/** 休出の未使用 */
		Optional<UnUserOfBreak> unUserOfBreak = unbalanceVacation.isPresent() ? Optional.of(new UnUserOfBreak(manageId,
				unbalanceVacation.get().getTimeOneDay().v(), unbalanceVacation.get().getDeadline(),
				numberOccurren.getTime().map(x -> x.v()).orElse(0), numberOccurren.getDay().v(),
				unbalanceVacation.get().getTimeHalfDay().v(), unbalanceNumber.getTime().map(x -> x.v()).orElse(0),
				unbalanceNumber.getDay().v(), unbalanceVacation.get().getDigestionCate(),
				unbalanceVacation.get().getExtinctionDate(), Optional.empty())) : Optional.empty();
		/** 代休の未相殺 */
		Optional<UnOffSetOfDayOff> unOffsetOfDayoff = Optional.of(new UnOffSetOfDayOff(manageId,
				numberOccurren.getTime().map(x -> x.v()).orElse(0), numberOccurren.getDay().v(),
				unbalanceNumber.getTime().map(x -> x.v()).orElse(0), unbalanceNumber.getDay().v()));

		return new BreakDayOffDetail(this.employeeId, dataAtr, dateOccur, occurrentClass, unUserOfBreak,
				unOffsetOfDayoff);
	}

	public AbsRecDetailPara convertDefaultAbs() {

		Optional<UnOffsetOfAbs> unOffsetAbs = Optional
				.of(new UnOffsetOfAbs(manageId, numberOccurren.getDay().v(), unbalanceNumber.getDay().v()));

		Optional<UnUseOfRec> unUseOfRec = unbalanceCompensation.isPresent() ? Optional
				.of(new UnUseOfRec(unbalanceCompensation.get().getDeadline(), manageId, numberOccurren.getDay().v(),
						unbalanceCompensation.get().getLegalInExClassi(), unbalanceNumber.getDay().v(), unbalanceCompensation.get().getDigestionCate(),
						unbalanceCompensation.get().getExtinctionDate(), Optional.empty()))
				: Optional.empty();

		return new AbsRecDetailPara(this.employeeId, dataAtr, dateOccur, occurrentClass, unOffsetAbs, unUseOfRec);
	}

	public boolean checkDataInPeriod(DatePeriod periodCheck) {

		if (!dateOccur.getDayoffDate().isPresent())
			return false;

		if (dateOccur.getDayoffDate().get().afterOrEquals(periodCheck.start())
				&& dateOccur.getDayoffDate().get().beforeOrEquals(periodCheck.end()) && !dateOccur.isUnknownDate()) {
			return true;
		}
		return false;
	}

	@Getter
	public static class AccuVacationBuilder {

		private String employeeId;

		/** 年月日 **/
		private CompensatoryDayoffDate dateOccur;

		/** 発生消化区分* */
		private OccurrenceDigClass occurrentClass;

		/** 状態 **/
		private MngDataStatus dataAtr;

		/** 残数管理データID **/
		private String manageId;

		/** 発生数 **/
		private NumberConsecuVacation numberOccurren;

		/** 未相殺数 */
		private NumberConsecuVacation unbalanceNumber;

		/**
		 * 休暇発生明細
		 */
		private Optional<UnbalanceVacation> unbalanceVacation;

		/**
		 * 振出の未相殺
		 */
		private Optional<UnbalanceCompensation> unbalanceCompensation;

		public AccuVacationBuilder(String employeeId, CompensatoryDayoffDate dateOccur,
				OccurrenceDigClass occurrentClass, MngDataStatus dataAtr, String manageId) {
			this.employeeId = employeeId;
			this.dateOccur = dateOccur;
			this.occurrentClass = occurrentClass;
			this.dataAtr = dataAtr;
			this.manageId = manageId;
			this.unbalanceVacation = Optional.empty();
			this.unbalanceCompensation = Optional.empty();
		}

		public AccuVacationBuilder numberOccurren(NumberConsecuVacation numberOccurren) {
			this.numberOccurren = numberOccurren;
			return this;
		}

		public AccuVacationBuilder unbalanceNumber(NumberConsecuVacation unbalanceNumber) {
			this.unbalanceNumber = unbalanceNumber;
			return this;
		}

		public AccuVacationBuilder unbalanceVacation(UnbalanceVacation unbalanceVacation) {
			this.unbalanceVacation = Optional.ofNullable(unbalanceVacation);
			return this;
		}

		public AccuVacationBuilder unbalanceCompensation(UnbalanceCompensation unbalanceCompensation) {
			this.unbalanceCompensation = Optional.ofNullable(unbalanceCompensation);
			return this;
		}

		public AccumulationAbsenceDetail build() {
			return new AccumulationAbsenceDetail(this);
		}

	}

	/**
	 * 逐次発生の休暇数
	 */
	@Data
	@AllArgsConstructor
	public static class NumberConsecuVacation {

		/**
		 * 日数
		 */
		private ManagementDataRemainUnit day;

		/**
		 * 時間
		 */
		private Optional<AttendanceTime> time;

		public boolean allFieldZero() {
			return day.v() == 0 && (!time.isPresent() || time.get().v() == 0);
		}

		public NumberConsecuVacation() {
			this.day = new ManagementDataRemainUnit(0d);
			this.time = Optional.empty();
		}

	}

}
