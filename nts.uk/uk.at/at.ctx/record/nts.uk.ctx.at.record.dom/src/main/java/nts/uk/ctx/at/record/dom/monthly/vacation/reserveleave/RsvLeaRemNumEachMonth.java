package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

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
 * 積立年休月別残数データ
 * @author shuichu_ishida
 */
@Getter
public class RsvLeaRemNumEachMonth extends AggregateRoot {

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
	/** 積立年休 */
	private ReserveLeave reserveLeave;
	/** 実積立年休 */
	private RealReserveLeave realReserveLeave;
	/** 積立年休付与情報 */
	private Optional<ReserveLeaveGrant> reserveLeaveGrant;
	/** 付与区分 */
	private boolean grantAtr;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 */
	public RsvLeaRemNumEachMonth(
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
		this.reserveLeave = new ReserveLeave();
		this.realReserveLeave = new RealReserveLeave();
		this.reserveLeaveGrant = Optional.empty();
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
	 * @param reserveLeave 積立年休
	 * @param realReserveLeave 実積立年休
	 * @param reserveLeaveGrant 積立年休付与情報
	 * @param grantAtr 付与区分
	 * @return 積立年休月別残数データ
	 */
	public static RsvLeaRemNumEachMonth of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod closurePeriod,
			ClosureStatus closureStatus,
			ReserveLeave reserveLeave,
			RealReserveLeave realReserveLeave,
			Optional<ReserveLeaveGrant> reserveLeaveGrant,
			boolean grantAtr){
		
		RsvLeaRemNumEachMonth domain = new RsvLeaRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
		domain.closurePeriod = closurePeriod;
		domain.closureStatus = closureStatus;
		domain.reserveLeave = reserveLeave;
		domain.realReserveLeave = realReserveLeave;
		domain.reserveLeaveGrant = reserveLeaveGrant;
		domain.grantAtr = grantAtr;
		return domain;
	}
}
