package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 基準社員の承認対象者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApprovalEmpStatus {
	
	/**
	 * 基準社員
	 */
	private String employeeID;
	
	/**
	 * 対象者承認状況リスト
	 */
	private List<RouteSituation> routeSituationLst;
	
}
