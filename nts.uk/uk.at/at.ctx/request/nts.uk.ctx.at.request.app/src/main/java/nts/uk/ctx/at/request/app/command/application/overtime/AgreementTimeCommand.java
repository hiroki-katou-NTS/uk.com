package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport_Old;

@AllArgsConstructor
@NoArgsConstructor
public class AgreementTimeCommand {
	/** 社員ID */
	public String employeeId;
	/** 確定情報 */
	public AgreeTimeOfMonthCommand confirmed;
	/** 申請反映後情報 */
	public AgreeTimeOfMonthCommand afterAppReflect;
	/** エラーメッセージ */
	public String errorMessage;
	
	public AgreementTimeImport_Old toDomain() {
		return new AgreementTimeImport_Old(
				employeeId,
				Optional.ofNullable(confirmed.toDomain()),
				Optional.ofNullable(afterAppReflect.toDomain()),
				Optional.empty(),
				Optional.empty(),
				Optional.ofNullable(errorMessage));
	}
}
