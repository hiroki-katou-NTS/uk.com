package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード認証による社員の識別失敗記録
 */
@RequiredArgsConstructor
public class PasswordAuthIdentificationFailureLog  implements DomainAggregate {
	
	/** 日時 */
	@Getter
	private final GeneralDateTime failureTimestamps;

	/** 指定された会社コード */
	@Getter
	private final String companyCode;

	/** 指定された社員コード */
	@Getter
	private final String employeeCode;

	public static PasswordAuthIdentificationFailureLog create(String companyCode, String employeeCode) {
		return new PasswordAuthIdentificationFailureLog(GeneralDateTime.now(), companyCode, employeeCode);
	}
}
