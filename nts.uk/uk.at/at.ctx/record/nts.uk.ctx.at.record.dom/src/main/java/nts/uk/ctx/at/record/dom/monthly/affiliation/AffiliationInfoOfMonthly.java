package nts.uk.ctx.at.record.dom.monthly.affiliation;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Getter
/** 月別実績の所属情報 */
public class AffiliationInfoOfMonthly {

	/** 年月: 年月 */
	private final YearMonth yearMonth;
	
	/** 月初の情報: 集計所属情報 */
	private AggregateAffiliationInfo startMonthInfo;
	
	/** 月末の情報: 集計所属情報 */
	private AggregateAffiliationInfo endMonthInfo;
	
	/** 社員ID: 社員ID */
	private final String employeeId;
	
	/** 締めID: 締めID */
	private final ClosureId closureId;
	
	/** 締め日: 日付 */
	private final ClosureDate closureDate;

	public AffiliationInfoOfMonthly(YearMonth yearMonth, AggregateAffiliationInfo startMonthInfo,
			AggregateAffiliationInfo endMonthInfo, String employeeId, ClosureId closureId, ClosureDate closureDate) {
		super();
		this.yearMonth = yearMonth;
		this.startMonthInfo = startMonthInfo;
		this.endMonthInfo = endMonthInfo;
		this.employeeId = employeeId;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}
}
