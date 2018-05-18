package nts.uk.ctx.at.record.dom.monthly.anyitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 月別実績の任意項目
 * @author shuichu_ishida
 */
@Getter
public class AnyItemOfMonthly extends AggregateRoot {

	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;
	/** 任意項目ID */
	private final int anyItemId;
	
	/** 時間 */
	private Optional<AnyTimeMonth> time;
	/** 回数 */
	private Optional<AnyTimesMonth> times;
	/** 金額 */
	private Optional<AnyAmountMonth> amount;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID
	 */
	public AnyItemOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, int anyItemId){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.anyItemId = anyItemId;
		this.time = Optional.empty();
		this.times = Optional.empty();
		this.amount = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID
	 * @param time 時間
	 * @param times 回数
	 * @param amount 金額
	 * @return 月別実績の任意項目
	 */
	public static AnyItemOfMonthly of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			int anyItemId,
			Optional<AnyTimeMonth> time,
			Optional<AnyTimesMonth> times,
			Optional<AnyAmountMonth> amount){
		
		AnyItemOfMonthly domain = new AnyItemOfMonthly(employeeId, yearMonth, closureId, closureDate, anyItemId);
		domain.time = time;
		domain.times = times;
		domain.amount = amount;
		return domain;
	}
}
