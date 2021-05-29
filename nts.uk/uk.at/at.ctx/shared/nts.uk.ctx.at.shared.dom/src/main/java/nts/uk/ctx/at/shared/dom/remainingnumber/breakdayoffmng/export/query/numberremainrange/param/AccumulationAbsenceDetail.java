package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

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

	// 基準日以降の紐づけされてる振出と相殺
	public ReserveLeaveRemainingDayNumber offsetOssociSwingAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<PayoutSubofHDManagement> lstPayoutSubs = require.getPayoutSubWithDateUse(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstPayoutSubs.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new ReserveLeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる振休と相殺
	public ReserveLeaveRemainingDayNumber offsetDigestSwingAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<PayoutSubofHDManagement> lstPayoutSubs = require.getPayoutSubWithOutbreakDay(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstPayoutSubs.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new ReserveLeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる休出と相殺
	public ReserveLeaveRemainingDayNumber offsetHolAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<LeaveComDayOffManagement> lstLeavCom = require.getLeaveComWithDateUse(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstLeavCom.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new ReserveLeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる代休と相殺
	public ReserveLeaveRemainingDayNumber offsetSubsHolAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<LeaveComDayOffManagement> lstLeavCom = require.getLeaveComWithOutbreakDay(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstLeavCom.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new ReserveLeaveRemainingDayNumber(x)));
	}

	public static interface Require {

		/**
		 * ＜条件＞ ・社員ID＝逐次発生の休暇明細.社員ID 
		 * ・使用日＝逐次発生の休暇明細．年月日．年月日 
		 * ・発生日 >= INPUT．基準日
		 */
		// PayoutSubofHDManaRepository.getPayoutSubWithDateUse
		List<PayoutSubofHDManagement> getPayoutSubWithDateUse(String sid, GeneralDate dateOfUse, GeneralDate baseDate);

		/**
		 * ＜条件＞ 逐次発生の休暇明細．年月日．日付不明 = false 
		 * ・社員ID＝逐次発生の休暇明細.社員ID 
		 * ・発生日＝逐次発生の休暇明細．年月日．年月日
		 * ・使用日 >= INPUT．基準日
		 */
		// PayoutSubofHDManaRepository.getPayoutSubWithOutbreakDay
		List<PayoutSubofHDManagement> getPayoutSubWithOutbreakDay(String sid, GeneralDate outbreakDay,
				GeneralDate baseDate);

		/**
		 * ＜条件＞ ・社員ID＝逐次発生の休暇明細.社員ID 
		 * ・使用日＝逐次発生の休暇明細．年月日．年月日 
		 * ・発生日 >= INPUT．基準日
		 */
		// LeaveComDayOffManaRepository.getLeaveComWithDateUse
		List<LeaveComDayOffManagement> getLeaveComWithDateUse(String sid, GeneralDate dateOfUse, GeneralDate baseDate);

		/**
		 * ＜条件＞ 逐次発生の休暇明細．年月日．日付不明 = false 
		 * ・社員ID＝逐次発生の休暇明細.社員ID 
		 * ・発生日＝逐次発生の休暇明細．年月日．年月日
		 * ・使用日 >= INPUT．基準日
		 */
		// LeaveComDayOffManaRepository.getLeaveComWithOutbreakDay
		List<LeaveComDayOffManagement> getLeaveComWithOutbreakDay(String sid, GeneralDate outbreakDay,
				GeneralDate baseDate);

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
