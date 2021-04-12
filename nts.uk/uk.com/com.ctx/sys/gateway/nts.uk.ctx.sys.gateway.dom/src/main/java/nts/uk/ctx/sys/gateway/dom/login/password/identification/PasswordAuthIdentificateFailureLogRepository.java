package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import java.util.List;

public interface PasswordAuthIdentificateFailureLogRepository {

	void insert(PasswordAuthIdentificateFailureLog domain);
	
	List<PasswordAuthIdentificateFailureLog> find(String companyId);

}
