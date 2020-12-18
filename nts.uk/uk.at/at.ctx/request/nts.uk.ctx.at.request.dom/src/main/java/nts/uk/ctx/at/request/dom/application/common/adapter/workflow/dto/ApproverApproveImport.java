package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverApproveImport {
	/**
	 * 対象日
	 */
	private GeneralDate date;
	
	/**
	 * 対象者
	 */
	private String employeeID;
	
	/**
	 * 承認者
	 */
	private List<ApproverEmpImport> authorList;
}
