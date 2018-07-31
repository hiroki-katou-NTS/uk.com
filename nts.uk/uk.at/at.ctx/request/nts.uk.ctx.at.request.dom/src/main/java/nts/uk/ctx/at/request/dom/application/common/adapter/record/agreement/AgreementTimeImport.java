package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 36協定時間一覧
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreementTimeImport {
	
	/** 社員ID */
	private String employeeId;
	/** 確定情報 */
	private Optional<AgreeTimeOfMonthExport> confirmed;
	/** 申請反映後情報 */
	private Optional<AgreeTimeOfMonthExport> afterAppReflect;
	/** エラーメッセージ */
	private Optional<String> errorMessage;
	
}
