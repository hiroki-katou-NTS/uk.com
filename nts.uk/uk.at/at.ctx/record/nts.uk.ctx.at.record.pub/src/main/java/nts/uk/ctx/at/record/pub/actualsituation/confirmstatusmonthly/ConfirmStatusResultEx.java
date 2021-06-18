package nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
/**
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ConfirmStatusResultEx {
	
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

	public ConfirmStatusResultEx(String employeeId, YearMonth yearMonth, ClosureId closureId, boolean confirmStatus,
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
