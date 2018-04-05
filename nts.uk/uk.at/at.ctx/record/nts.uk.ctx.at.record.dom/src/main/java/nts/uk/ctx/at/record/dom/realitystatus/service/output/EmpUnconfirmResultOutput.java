package nts.uk.ctx.at.record.dom.realitystatus.service.output;

import java.util.List;

import lombok.Value;

@Value
public class EmpUnconfirmResultOutput {
	/**
	 * 結果（OK/NG)
	 */
	private boolean OK;
	/**
	 * 未確認者＜社員ID、社員名、メールアドレス＞（リスト）
	 */
	private List<EmpUnconfirmOutput> listEmpUmconfirm;
}
