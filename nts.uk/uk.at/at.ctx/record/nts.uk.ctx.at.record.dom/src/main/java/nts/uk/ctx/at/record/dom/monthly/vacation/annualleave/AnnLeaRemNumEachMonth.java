package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年休月別残数データ
 * @author shuichu_ishida
 */
@Getter
public class AnnLeaRemNumEachMonth extends AggregateRoot {

	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;

	/** 締め期間 */
	private DatePeriod closurePeriod;
	/** 締め処理状態 */
	private ClosureStatus closureStatus;
	/** 年休 */
	private AnnualLeave annualLeave;
	/** 実年休 */
	private RealAnnualLeave realAnnualLeave;
	/** 半日年休 */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeave;
	/** 実半日年休 */
	private Optional<HalfDayAnnualLeave> realHalfDayAnnualLeave;
	/** 年休付与情報 */
	private Optional<AnnualLeaveGrant> annualLeaveGrant;
	/** 上限残時間 */
	private Optional<AnnualLeaveMaxRemainingTime> maxRemainingTime;
	/** 実上限残時間 */
	private Optional<AnnualLeaveMaxRemainingTime> realMaxRemainingTime;
	/** 年休出勤率日数 */
	private AnnualLeaveAttdRateDays attendanceRateDays;
	/** 付与区分 */
	private boolean grantAtr;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 */
	public AnnLeaRemNumEachMonth(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		
		this.closurePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.closureStatus = ClosureStatus.UNTREATED;
		this.annualLeave = new AnnualLeave();
		this.realAnnualLeave = new RealAnnualLeave();
		this.halfDayAnnualLeave = Optional.empty();
		this.realHalfDayAnnualLeave = Optional.empty();
		this.annualLeaveGrant = Optional.empty();
		this.maxRemainingTime = Optional.empty();
		this.realMaxRemainingTime = Optional.empty();
		this.attendanceRateDays = new AnnualLeaveAttdRateDays();
		this.grantAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param closurePeriod 締め期間
	 * @param closureStatus 締め処理状態
	 * @param annualLeave 年休
	 * @param realAnnualLeave 実年休
	 * @param halfDayAnnualLeave 半日年休
	 * @param realHalfDayAnnualLeave 実半日年休
	 * @param annualLeaveGrant 年休付与情報
	 * @param maxRemainingTime 上限残時間
	 * @param realMaxRemainingTime 実上限残時間
	 * @param attendanceRateDays 年休出勤率日数
	 * @param grantAtr 付与区分
	 * @return 年休月別残数データ
	 */
	public static AnnLeaRemNumEachMonth of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod closurePeriod,
			ClosureStatus closureStatus,
			AnnualLeave annualLeave,
			RealAnnualLeave realAnnualLeave,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeave,
			Optional<HalfDayAnnualLeave> realHalfDayAnnualLeave,
			Optional<AnnualLeaveGrant> annualLeaveGrant,
			Optional<AnnualLeaveMaxRemainingTime> maxRemainingTime,
			Optional<AnnualLeaveMaxRemainingTime> realMaxRemainingTime,
			AnnualLeaveAttdRateDays attendanceRateDays,
			boolean grantAtr){
		
		AnnLeaRemNumEachMonth domain = new AnnLeaRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
		domain.closurePeriod = closurePeriod;
		domain.closureStatus = closureStatus;
		domain.annualLeave = annualLeave;
		domain.realAnnualLeave = realAnnualLeave;
		domain.halfDayAnnualLeave = halfDayAnnualLeave;
		domain.realHalfDayAnnualLeave = realHalfDayAnnualLeave;
		domain.annualLeaveGrant = annualLeaveGrant;
		domain.maxRemainingTime = maxRemainingTime;
		domain.realMaxRemainingTime = realMaxRemainingTime;
		domain.attendanceRateDays = attendanceRateDays;
		domain.grantAtr = grantAtr;
		return domain;
	}
}
