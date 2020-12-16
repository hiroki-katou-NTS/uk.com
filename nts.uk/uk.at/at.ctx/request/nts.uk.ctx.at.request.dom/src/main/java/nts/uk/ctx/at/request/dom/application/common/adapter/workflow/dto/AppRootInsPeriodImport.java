package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class AppRootInsPeriodImport {
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 承認ルート一覧
	 */
	private List<AppRootInsImport> appRootInstanceLst;
}
