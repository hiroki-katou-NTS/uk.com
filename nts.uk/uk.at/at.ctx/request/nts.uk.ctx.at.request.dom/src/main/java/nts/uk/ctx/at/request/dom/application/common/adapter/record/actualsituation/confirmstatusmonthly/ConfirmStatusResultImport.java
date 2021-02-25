package nts.uk.ctx.at.request.dom.application.common.adapter.record.actualsituation.confirmstatusmonthly;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Getter
public class ConfirmStatusResultImport {
	/**対象社員*/
	private String employeeId;
	
	/**対象年月*/
	private YearMonth yearMonth;
	
	/**対象締め*/
	private ClosureId closureId;
	
	/**確認状況*/
	private boolean confirmStatus;
	
	/**実施可否 : 実施できない(0),実施できる(1)*/
	private int implementaPropriety;

	/**解除可否: 解除できない(0),解除できる(1)*/
	private int whetherToRelease;

	public ConfirmStatusResultImport(String employeeId, YearMonth yearMonth, ClosureId closureId, boolean confirmStatus,
			int implementaPropriety, int whetherToRelease) {
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.confirmStatus = confirmStatus;
		this.implementaPropriety = implementaPropriety;
		this.whetherToRelease = whetherToRelease;
	}
}
