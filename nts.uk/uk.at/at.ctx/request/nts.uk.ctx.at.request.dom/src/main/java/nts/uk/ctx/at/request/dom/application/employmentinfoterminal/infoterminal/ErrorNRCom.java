package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         エラーNR-通信
 */
public class ErrorNRCom {

	/**
	 * GUID
	 */
	@Getter
	private final String id;

	/**
	 * コード
	 */
	@Getter
	private final Integer empInfoTerCode;

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
	private final String messageDisplay;

	public ErrorNRCom(ErrorNRComBuilder builder) {
		super();
		this.id = builder.id;
		this.empInfoTerCode = builder.empInfoTerCode;
		this.companyId = builder.companyId;
		this.employeeId = builder.employeeId;
		this.dateCreateError = builder.dateCreateError;
		this.dateErrorTranfer = builder.dateErrorTranfer;
		this.typeError = builder.typeError;
		this.messageDisplay = builder.messageDisplay;
	}

	public static class ErrorNRComBuilder {
		/**
		 * GUID
		 */
		private String id;

		/**
		 * コード
		 */
		private Integer empInfoTerCode;

		/**
		 * 会社ID
		 */
		private CompanyId companyId;

		/**
		 * 社員ID
		 */
		private EmployeeId employeeId;

		/**
		 * エラーが発生した日
		 */
		private GeneralDateTime dateCreateError;

		/**
		 * エラー転送の日付
		 */
		private Optional<GeneralDateTime> dateErrorTranfer;

		/**
		 * 種類
		 */
		private NRErrorType typeError;

		/**
		 * メッセージ
		 */
		private String messageDisplay;

		public ErrorNRComBuilder(String id, Integer empInfoTerCode, CompanyId companyId, EmployeeId employeeId,
				GeneralDateTime dateCreateError, Optional<GeneralDateTime> dateErrorTranfer) {
			this.id = id;
			this.empInfoTerCode = empInfoTerCode;
			this.companyId = companyId;
			this.employeeId = employeeId;
			this.dateCreateError = dateCreateError;
			this.dateErrorTranfer = dateErrorTranfer;
		}

		public ErrorNRComBuilder typeError(NRErrorType typeError) {
			this.typeError = typeError;
			return this;
		}

		public ErrorNRComBuilder createMessage(String messageDisplay) {
			this.messageDisplay = messageDisplay;
			return this;
		}

		public ErrorNRCom build() {
			return new ErrorNRCom(this);
		}
	}

}
