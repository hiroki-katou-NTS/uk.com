package nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.approval.monthly;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 月別実績の承認状況
 * @author tutk 
 * create but not use because EA change: have Domain,Entity
 */
@Getter
public class ApprovalStatusMonthlyResult extends AggregateRoot {
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 年月
	 */
	private YearMonth processDate;
	
	/**
	 * 締めID
	 */
	private ClosureId closureId;
	
	/**
	 * 締め日
	 */
	private Day closingDate;
	/**
	 * 承認ルートID
	 */
	private String rootInstanceID;
	
	public ApprovalStatusMonthlyResult(String employeeId, YearMonth processDate, ClosureId closureId, Day closingDate,
			String rootInstanceID) {
		super();
		this.employeeId = employeeId;
		this.processDate = processDate;
		this.closureId = closureId;
		this.closingDate = closingDate;
		this.rootInstanceID = rootInstanceID;
	}
	
	
}
