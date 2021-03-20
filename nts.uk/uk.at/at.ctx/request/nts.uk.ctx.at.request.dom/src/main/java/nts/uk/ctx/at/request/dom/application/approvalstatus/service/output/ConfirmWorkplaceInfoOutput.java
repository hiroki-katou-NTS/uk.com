package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ConfirmWorkplaceInfoOutput {
	/**
	 * 職場管理者＜社員ID、社員名＞（リスト）
	 */
	private List<EmployeeEmailImport> authorLst;
	
	/**
	 * 含まれる　（True/False）
	 */
	private boolean isAuthor;
	
	/**
	 * <所属職場権限>．利用できる（Boolean)
	 */
	private boolean authorUse;
}
