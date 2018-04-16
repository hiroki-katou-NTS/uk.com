package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年休月別残数データ
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainNumEachMonth extends AggregateRoot {

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
	/** 実年休付与前 */
	private Optional<RealAnnualLeave> realAnnualLeaveBeforeGrant;
	/** 半日年休 */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeave;
	/** 年休付与情報 */
	private Optional<AnnualLeaveGrant> annualLeaveGrant;
	/** 上限残時間 */
	private Optional<AnnualLeaveMaxRemainingTime> maxRemainingTime;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 */
	public AnnualLeaveRemainNumEachMonth(
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
		this.realAnnualLeaveBeforeGrant = Optional.empty();
		this.halfDayAnnualLeave = Optional.empty();
		this.annualLeaveGrant = Optional.empty();
		this.maxRemainingTime = Optional.empty();
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
	 * @param realAnnualLeaveBeforeGrant 実年休付与前
	 * @param halfDayAnnualLeave 半日年休
	 * @param annualLeaveGrant 年休付与情報
	 * @param maxRemainingTime 上限残時間
	 * @return 年休月別残数データ
	 */
	public static AnnualLeaveRemainNumEachMonth of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod closurePeriod,
			ClosureStatus closureStatus,
			AnnualLeave annualLeave,
			RealAnnualLeave realAnnualLeave,
			Optional<RealAnnualLeave> realAnnualLeaveBeforeGrant,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeave,
			Optional<AnnualLeaveGrant> annualLeaveGrant,
			Optional<AnnualLeaveMaxRemainingTime> maxRemainingTime){
		
		AnnualLeaveRemainNumEachMonth domain = new AnnualLeaveRemainNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
		domain.closurePeriod = closurePeriod;
		domain.closureStatus = closureStatus;
		domain.annualLeave = annualLeave;
		domain.realAnnualLeave = realAnnualLeave;
		domain.realAnnualLeaveBeforeGrant = realAnnualLeaveBeforeGrant;
		domain.halfDayAnnualLeave = halfDayAnnualLeave;
		domain.annualLeaveGrant = annualLeaveGrant;
		domain.maxRemainingTime = maxRemainingTime;
		return domain;
	}
}
