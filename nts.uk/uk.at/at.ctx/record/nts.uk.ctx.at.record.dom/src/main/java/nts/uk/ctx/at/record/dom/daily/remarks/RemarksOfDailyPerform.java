package nts.uk.ctx.at.record.dom.daily.remarks;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RecordRemarks;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;

/** 日別実績の備考 */
@Getter
public class RemarksOfDailyPerform {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 備考 */
	private RemarksOfDailyAttd remarks;
	

	public RemarksOfDailyPerform(String employeeId, GeneralDate ymd, RecordRemarks remarks, int remarkNo) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.remarks = new RemarksOfDailyAttd(remarks, remarkNo);
	}

	public RemarksOfDailyPerform() {	
		super();
	}

	public RemarksOfDailyPerform(String employeeId, GeneralDate ymd, RemarksOfDailyAttd remarks) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.remarks = remarks;
	}
	
	
}
