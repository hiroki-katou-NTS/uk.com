package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;

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
	// private Optional<UnbalanceVacation> unbalanceVacation;

	/**
	 * 振出の未相殺
	 */
	// private Optional<UnbalanceCompensation> unbalanceCompensation;

	public AccumulationAbsenceDetail(AccuVacationBuilder builder) {

		this.employeeId = builder.getEmployeeId();

		this.dateOccur = builder.getDateOccur();

		this.occurrentClass = builder.getOccurrentClass();

		this.dataAtr = builder.getDataAtr();

		this.manageId = builder.getManageId();

		this.numberOccurren = builder.getNumberOccurren();

		this.unbalanceNumber = builder.getUnbalanceNumber();

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
		// private Optional<UnbalanceVacation> unbalanceVacation;

		/**
		 * 振出の未相殺
		 */
		// private Optional<UnbalanceCompensation> unbalanceCompensation;

		public AccuVacationBuilder(String employeeId, CompensatoryDayoffDate dateOccur,
				OccurrenceDigClass occurrentClass, MngDataStatus dataAtr, String manageId) {
			this.employeeId = employeeId;
			this.dateOccur = dateOccur;
			this.occurrentClass = occurrentClass;
			this.dataAtr = dataAtr;
			this.manageId = manageId;
			// this.unbalanceVacation = Optional.empty();
			// this.unbalanceCompensation = Optional.empty();
		}

		public AccuVacationBuilder numberOccurren(NumberConsecuVacation numberOccurren) {
			this.numberOccurren = numberOccurren;
			return this;
		}

		public AccuVacationBuilder unbalanceNumber(NumberConsecuVacation unbalanceNumber) {
			this.unbalanceNumber = unbalanceNumber;
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
