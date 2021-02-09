package nts.uk.ctx.sys.gateway.dom.login.identification;

import java.time.LocalDateTime;

import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * パスワード認証による社員の識別失敗記録
 */
@RequiredArgsConstructor
public class PasswordAuthIdentificationFailureLog  implements DomainAggregate {
	
	@Inject PasswordAuthIdentificationFailureLogRepository failureLogRepository;
	
	/** 日時 */
	@Getter
	private final LocalDateTime failureTimestamps;

	/** 指定された会社コード */
	@Getter
	private final String companyCode;

	/** 指定された社員コード */
	@Getter
	private final String employeeCode;

	/** IPアドレス */
	@Getter
	private final String ipAddress;
	
	public void add(String companyId, String employeeCode, String ipAddress) {
		failureLogRepository.insert(new PasswordAuthIdentificationFailureLog(LocalDateTime.now(), companyId, employeeCode, ipAddress));
	}
}
