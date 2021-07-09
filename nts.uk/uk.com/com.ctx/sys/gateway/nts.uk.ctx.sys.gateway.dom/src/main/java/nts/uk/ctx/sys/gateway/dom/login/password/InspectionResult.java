package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.sys.shared.dom.employee.GetAnEmployeeImported;

/**
 * 検証結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.GateWay.ログイン.パスワード認証.社員コードとパスワードを検証する.検証結果
 * @author chungnt
 *
 */

@Data
public class InspectionResult {

	// 検証成功
	private boolean verificationSuccess;
	
	// 社員情報
	private Optional<GetAnEmployeeImported> employeeInformation;
	
	// 検証失敗メッセージ
	private Optional<String> verificationFailureMessage;
	
	/**
	 * 	[C-1] 検証成功
	 * @param employee
	 */
	public InspectionResult(GetAnEmployeeImported employee) {
		super();
		this.verificationSuccess = true;
		this.employeeInformation = Optional.ofNullable(employee);
		this.verificationFailureMessage = Optional.empty();
	}
	
	/**
	 * 	[C-2] ユーザ検証失敗
	 */
	public InspectionResult() {
		super();
		this.verificationSuccess = false;
		this.employeeInformation = Optional.empty();
		this.verificationFailureMessage = Optional.of("Msg_301");
	}
	
	/**
	 * 	[C-3] パスワード検証失敗
	 */
	public InspectionResult(String param) {
		super();
		this.verificationSuccess = false;
		this.employeeInformation = Optional.empty();
		this.verificationFailureMessage = Optional.of("Msg_302");
	}
}
