package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         エラーNR-通信
 */
public class ErrorNRCom implements DomainAggregate {

	/**
	 * GUID
	 */
	@Getter
	private final String id;

	/**
	 * コード
	 */
	@Getter
	private final EmpInfoTerminalCode empInfoTerCode;

	/**
	 * 会社ID
	 */
	@Getter
	private final CompanyId companyId;

	/**
	 * 社員ID
	 */
	@Getter
	private final EmployeeId employeeId;

	/**
	 * エラーが発生した日
	 */
	@Getter
	private final GeneralDateTime dateCreateError;

	/**
	 * エラー転送の日付
	 */
	@Getter
	private final Optional<GeneralDateTime> dateErrorTranfer;

	/**
	 * 種類
	 */
	@Getter
	private final NRErrorType typeError;

	/**
	 * メッセージ
	 */
	@Getter
	private final MessageDisplay messageDisplay;

	public ErrorNRCom(String id, EmpInfoTerminalCode empInfoTerCode, CompanyId companyId, EmployeeId employeeId,
			GeneralDateTime dateCreateError, Optional<GeneralDateTime> dateErrorTranfer, NRErrorType typeError,
			MessageDisplay messageDisplay) {
		super();
		this.id = id;
		this.empInfoTerCode = empInfoTerCode;
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.dateCreateError = dateCreateError;
		this.dateErrorTranfer = dateErrorTranfer;
		this.typeError = typeError;
		this.messageDisplay = messageDisplay;
	}

}
