package nts.uk.ctx.at.record.dom.daily.remarks;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/** 日別実績の備考 */
@Getter
public class RemarksOfDailyPerform {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 備考: 日別実績の備考 */
	@Setter
	private RecordRemarks remarks;
	
	/** 備考欄NO: int */
	private int remarkNo;

	public RemarksOfDailyPerform(String employeeId, GeneralDate ymd, RecordRemarks remarks, int remarkNo) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.remarks = remarks;
		this.remarkNo = remarkNo;
	}

	public RemarksOfDailyPerform() {
		super();
	}
}
