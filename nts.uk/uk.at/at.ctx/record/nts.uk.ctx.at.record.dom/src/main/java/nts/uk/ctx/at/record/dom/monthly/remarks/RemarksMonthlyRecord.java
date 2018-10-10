package nts.uk.ctx.at.record.dom.monthly.remarks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author phongtq
 *月別実績の備考
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RemarksMonthlyRecord extends AggregateRoot{
	/** 社員ID */
	private String employeeId;
	
	/** 締めID */
	private ClosureId closureId;
	
	/** 備考欄NO */
	public int remarksNo;
	
	/** 年月 */
	private YearMonth remarksYM;
	
	/** 締め日 */
	private ClosureDate closureDate;
	
	/** 期間 */
	private DatePeriod remarksPeriod;
	
	/** 備考 */
	public RecordRemarks recordRemarks;

}
