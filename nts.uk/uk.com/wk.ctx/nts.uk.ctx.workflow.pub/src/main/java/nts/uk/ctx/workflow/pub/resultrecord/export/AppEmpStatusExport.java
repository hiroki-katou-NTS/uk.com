package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 基準社員の承認対象者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppEmpStatusExport {
	/**
	 * 基準社員
	 */
	private String employeeID;
	
	/**
	 * 対象者承認状況リスト
	 */
	private List<RouteSituationExport> routeSituationLst;
}
