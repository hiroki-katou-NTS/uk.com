package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード認証による社員の識別失敗記録
 */
@RequiredArgsConstructor
public class PasswordAuthIdentificateFailureLog  implements DomainAggregate {
	
	/** 失敗日時 */
	@Getter
	private final GeneralDateTime failureTimestamps;

	/** 試行した会社コード */
	@Getter
	private final String companyCode;

	/** 試行した社員コード */
	@Getter
	private final String employeeCode;

	public static PasswordAuthIdentificateFailureLog create(String companyCode, String employeeCode) {
		return new PasswordAuthIdentificateFailureLog(GeneralDateTime.now(), companyCode, employeeCode);
	}
}
