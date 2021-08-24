package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author ThanhNX
 *
 *         逐次発生の休暇明細
 */
@Getter
public class AccumulationAbsenceDetail implements Cloneable{

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

	// 基準日以降の紐づけされてる振出日数を取得
	public LeaveRemainingDayNumber offsetOssociSwingAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<PayoutSubofHDManagement> lstPayoutSubs = require.getPayoutSubWithDateUse(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstPayoutSubs.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new LeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる振休日数を取得
	public LeaveRemainingDayNumber offsetDigestSwingAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<PayoutSubofHDManagement> lstPayoutSubs = require.getPayoutSubWithOutbreakDay(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstPayoutSubs.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new LeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる休出日数を取得
	public LeaveRemainingDayNumber offsetHolAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<LeaveComDayOffManagement> lstLeavCom = require.getLeaveComWithDateUse(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstLeavCom.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new LeaveRemainingDayNumber(x)));
	}

	// 基準日以降の紐づけされてる代休日数を取得
	public LeaveRemainingDayNumber offsetSubsHolAftRefDate(Require require, GeneralDate baseDate) {
		// Require．基準日以降の紐づけを取得
		List<LeaveComDayOffManagement> lstLeavCom = require.getLeaveComWithOutbreakDay(employeeId,
				dateOccur.getDayoffDate().get(), baseDate);
		return lstLeavCom.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.getAssocialInfo().getDayNumberUsed().v()),
						x -> new LeaveRemainingDayNumber(x)));
	}

	// 暫定振出管理データを変換する
	public Optional<InterimRecMng> getRecMng() {
		
		if (this.dateOccur.isUnknownDate() || this.dataAtr == MngDataStatus.CONFIRMED
				|| this.occurrentClass == OccurrenceDigClass.DIGESTION) {
			return Optional.empty();
		}
		
		return Optional.of(new InterimRecMng(this.getManageId(), this.getEmployeeId(), this.dateOccur.getDayoffDate().get(), 
				this.getDataAtr().getCreateAtr(),
				RemainType.PICKINGUP,
				 ((UnbalanceCompensation) this).getDeadline(), 
				 new OccurrenceDay(this.numberOccurren.getDay().v()),
				new UnUsedDay(this.unbalanceNumber.getDay().v())));
	}
	
	//暫定振休管理データを変換する
	public Optional<InterimAbsMng> getAbsMng() {

		if (this.dateOccur.isUnknownDate() || this.dataAtr == MngDataStatus.CONFIRMED
				|| this.occurrentClass == OccurrenceDigClass.OCCURRENCE) {
			return Optional.empty();
		}

		return Optional.of(new InterimAbsMng(this.getManageId(), this.getEmployeeId(),
				this.dateOccur.getDayoffDate().get(), this.getDataAtr().getCreateAtr(), RemainType.PAUSE,
				new RequiredDay(this.numberOccurren.getDay().v()), new UnOffsetDay(this.unbalanceNumber.getDay().v())));
	}
	
	// 暫定休出管理データを変換する
	public Optional<InterimBreakMng> getBreakMng() {

		if (this.dateOccur.isUnknownDate() || this.dataAtr == MngDataStatus.CONFIRMED
				|| this.occurrentClass == OccurrenceDigClass.DIGESTION) {
			return Optional.empty();
		}

		return Optional.of(new InterimBreakMng(this.getManageId(), this.getEmployeeId(), this.dateOccur.getDayoffDate().get(), 
				this.getDataAtr().getCreateAtr(),
				RemainType.BREAK, 
				((UnbalanceVacation)this).getTimeOneDay(),
				((UnbalanceVacation)this).getDeadline(), 
				this.getNumberOccurren().getTime().map(x -> new OccurrenceTime(x.v())).orElse(new OccurrenceTime(0)), 
				new OccurrenceDay(this.getNumberOccurren().getDay().v()), 
				((UnbalanceVacation)this).getTimeHalfDay(), 
				this.getUnbalanceNumber().getTime().map(x -> new UnUsedTime(x.v())).orElse(new UnUsedTime(0)),
				new UnUsedDay(this.getUnbalanceNumber().getDay().v())
				));
	}
	
	// 暫定代休管理データを変換する
	public Optional<InterimDayOffMng> getDayOffMng() {

		if (this.dateOccur.isUnknownDate() || this.dataAtr == MngDataStatus.CONFIRMED
				|| this.occurrentClass == OccurrenceDigClass.OCCURRENCE) {
			return Optional.empty();
		}

		return Optional.of(new InterimDayOffMng(this.getManageId(), this.getEmployeeId(), this.dateOccur.getDayoffDate().get(), 
				this.getDataAtr().getCreateAtr(),
				RemainType.SUBHOLIDAY, 
				this.numberOccurren.getTime().map(x -> new RequiredTime(x.v())).orElse(new RequiredTime(0)),
				new RequiredDay(this.numberOccurren.getDay().v()), 
				this.getUnbalanceNumber().getTime().map(x -> new UnOffsetTime(x.v())).orElse(new UnOffsetTime(0)), 
				new UnOffsetDay(this.getUnbalanceNumber().getDay().v()), 
				Optional.empty()));
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

	private AccumulationAbsenceDetail cloneDetail() {
		return new AccuVacationBuilder(this.getEmployeeId(), this.getDateOccur(), this.getOccurrentClass(),
				this.getDataAtr(), this.getManageId()).numberOccurren(
						new NumberConsecuVacation(new ManagementDataRemainUnit(this.getNumberOccurren().getDay().v()),
								this.getNumberOccurren().getTime()))
				        .unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(this.getUnbalanceNumber().getDay().v()),
								this.getUnbalanceNumber().getTime()))
						.build();
	}

	@Override
	public AccumulationAbsenceDetail clone() {
		if (this instanceof UnbalanceCompensation) {
			val detail = (UnbalanceCompensation) this;
			return new UnbalanceCompensation(cloneDetail(), detail.getDeadline(), detail.getDigestionCate(),
					detail.getExtinctionDate(), detail.getLegalInExClassi());
		} else if (this instanceof UnbalanceVacation) {
			val detail = (UnbalanceVacation) this;
			return new UnbalanceVacation(detail.getDeadline(), detail.getDigestionCate(), detail.getExtinctionDate(),
					cloneDetail(), detail.getTimeOneDay(), detail.getTimeHalfDay());
		} else {
			return cloneDetail();
		}
	}
}
