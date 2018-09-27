package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * ルート状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RouteSituationExport {
	
	/**
	 * 対象日
	 */
	private GeneralDate date;
	
	/**
	 * 対象者
	 */
	private String employeeID;
	
	/**
	 * ルートの状況
	 */
	private Integer approverEmpState;
	
	/**
	 * 承認状況
	 */
	private Optional<ApprovalStatusExport> approvalStatus;
}
