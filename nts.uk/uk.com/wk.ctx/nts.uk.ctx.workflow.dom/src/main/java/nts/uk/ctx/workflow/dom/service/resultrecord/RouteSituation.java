package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * ルート状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RouteSituation {
	
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
	private ApproverEmpState approverEmpState;
	
	/**
	 * 承認状況
	 */
	private Optional<ApprovalStatus> approvalStatus;
	
}
