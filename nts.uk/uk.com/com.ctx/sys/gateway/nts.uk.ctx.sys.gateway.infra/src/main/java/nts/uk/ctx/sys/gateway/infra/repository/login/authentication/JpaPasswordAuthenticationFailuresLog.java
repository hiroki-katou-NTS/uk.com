package nts.uk.ctx.sys.gateway.infra.repository.login.authentication;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticationFailuresLogRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.authnticate.SgwdtFailLogPasswordAuth;

public class JpaPasswordAuthenticationFailuresLog extends JpaRepository implements PasswordAuthenticationFailuresLogRepository{
	
	private List<SgwdtFailLogPasswordAuth> toEntity(PasswordAuthenticationFailuresLog log) {
		return log.getFailureTimestamps().stream()
					.map(failTime -> new SgwdtFailLogPasswordAuth(failTime, log.getTriedUserId(), log.getTriedPassword()))
					.collect(Collectors.toList());
	}
	
	public void insert(PasswordAuthenticationFailuresLog log) {
		this.commandProxy().insertAll(toEntity(log));
	}

}
