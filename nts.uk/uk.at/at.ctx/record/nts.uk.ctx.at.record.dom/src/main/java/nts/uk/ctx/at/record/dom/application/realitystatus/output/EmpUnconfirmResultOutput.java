package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.EmployeeUnconfirmImport;

@Value
public class EmpUnconfirmResultOutput {
	/**
	 * 結果（OK/NG)
	 */
	private boolean OK;
	/**
	 * 未確認者＜社員ID、社員名、メールアドレス＞（リスト）
	 */
	private List<EmployeeUnconfirmImport> listEmpUmconfirm;
}
