package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;

/**
 * パスワード認証による社員の識別失敗記録
 */
@Getter
public class PasswordAuthIdentificateFailureLog  implements DomainAggregate {
	
	/** 失敗日時 */
	private final GeneralDateTime failureDateTime;
	/** 試行した会社コード */
	private final String triedCompanyId;
	/** 試行した社員コード */
	private final String triedEmployeeCode;
	
	public PasswordAuthIdentificateFailureLog(GeneralDateTime dateTime, String companyId, String employeeCode) {
		this.failureDateTime = dateTime;
		this.triedCompanyId = companyId;
		// ユーザー入力の値は適当な長さでカットして保持する
		this.triedEmployeeCode = StringUtil.cutOffAsLengthHalf(employeeCode, 100);
	}
	
	/**
	 * いま失敗した
	 * @param userId
	 * @param password
	 * @return
	 */
	public static PasswordAuthIdentificateFailureLog create(String companyId, String employeeCode) {
		return new PasswordAuthIdentificateFailureLog(GeneralDateTime.now() , companyId, employeeCode);
	}
}
