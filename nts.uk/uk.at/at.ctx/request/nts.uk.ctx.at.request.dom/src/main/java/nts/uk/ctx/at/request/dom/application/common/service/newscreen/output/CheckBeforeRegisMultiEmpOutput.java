package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
public class CheckBeforeRegisMultiEmpOutput {
	
	/**
	 * List＜社員ID, 承認ルートの内容＞
	 */
	private Map<String, ApprovalRootContentImport_New> mapEmpContentAppr;
	
	/**
	 * エラー対象者のビジネスネーム
	 */
	private String empErrorName;
	
	public CheckBeforeRegisMultiEmpOutput() {
		this.mapEmpContentAppr = new HashMap<>();
		this.empErrorName = "";
	}
}
