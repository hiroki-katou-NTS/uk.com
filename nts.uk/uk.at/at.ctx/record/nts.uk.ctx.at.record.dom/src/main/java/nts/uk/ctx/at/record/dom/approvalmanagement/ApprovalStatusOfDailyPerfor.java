package nts.uk.ctx.at.record.dom.approvalmanagement;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績の承認状況 - root
 *
 */
@Getter
public class ApprovalStatusOfDailyPerfor extends AggregateRoot {

	private String employeeId;
	
	private GeneralDate ymd;
	
	private String rootInstanceID;

	public ApprovalStatusOfDailyPerfor(String employeeId, GeneralDate ymd, String rootInstanceID) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.rootInstanceID = rootInstanceID;
	}
}
