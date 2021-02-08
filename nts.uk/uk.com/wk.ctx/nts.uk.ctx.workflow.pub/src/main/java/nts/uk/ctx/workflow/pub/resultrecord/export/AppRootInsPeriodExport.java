package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootInsPeriodExport {
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 承認ルート一覧
	 */
	private List<AppRootInsExport> appRootInstanceLst;
}
