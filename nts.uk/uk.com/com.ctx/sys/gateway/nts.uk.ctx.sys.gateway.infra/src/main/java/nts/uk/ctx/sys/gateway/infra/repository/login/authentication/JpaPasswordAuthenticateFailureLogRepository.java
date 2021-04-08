package nts.uk.ctx.sys.gateway.infra.repository.login.authentication;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateFailureLog;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateFailureLogRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.authnticate.SgwdtFailLogPasswordAuth;

public class JpaPasswordAuthenticateFailureLogRepository extends JpaRepository implements PasswordAuthenticateFailureLogRepository{
	
	private List<SgwdtFailLogPasswordAuth> toEntity(PasswordAuthenticateFailureLog log) {
		return log.getFailureTimestamps().stream()
					.map(failTime -> new SgwdtFailLogPasswordAuth(failTime, log.getTriedUserId(), log.getTriedPassword()))
					.collect(Collectors.toList());
	}
	
	public void insert(PasswordAuthenticateFailureLog log) {
		this.commandProxy().insertAll(toEntity(log));
	}

}
