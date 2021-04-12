package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.List;

import nts.arc.time.GeneralDateTime;

public interface PasswordAuthenticateFailureLogRepository {
	
	void insert(PasswordAuthenticateFailureLog domain);
	
	List<PasswordAuthenticateFailureLog> find(String userId);
	
	List<PasswordAuthenticateFailureLog> find(String userId, GeneralDateTime startDateTime, GeneralDateTime endDateTime);
}
