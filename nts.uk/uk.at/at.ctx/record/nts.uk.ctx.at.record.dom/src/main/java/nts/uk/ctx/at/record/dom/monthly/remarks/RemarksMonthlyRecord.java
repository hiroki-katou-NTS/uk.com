package nts.uk.ctx.at.record.dom.monthly.remarks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdMinutes;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.common.Day;
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


	public RemarksMonthlyRecord(
			String employeeId,
			ClosureId closureId,
			int remarksNo,
			YearMonth remarksYM,
			ClosureDate closureDate){
		
		this.employeeId = employeeId;
		this.closureId = closureId;
		this.remarksNo = remarksNo;
		this.remarksYM = remarksYM;
		this.closureDate = closureDate;
		this.remarksPeriod = new DatePeriod(GeneralDate.min(), GeneralDate.min());
		this.recordRemarks = new RecordRemarks(null);
	}

}
