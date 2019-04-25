package nts.uk.ctx.sys.auth.pubimp.changelog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.pub.changelog.PassWordChangeLogPub;
import nts.uk.ctx.sys.auth.pub.changelog.PasswordChangeLogOut;
@Stateless
public class PassWordChangeLogPubImp implements PassWordChangeLogPub{

	@Inject
	private PasswordChangeLogRepository pwChangeLogRepo;
	
	@Override
	public List<PasswordChangeLogOut> getListPwChangeLog(String userId) {
		return pwChangeLogRepo.getListPwChangeLog(userId).stream()
				.map(c -> new PasswordChangeLogOut(c.getLogID(),
						c.getUserID(),
						c.getModifiedDate(),
						c.getPassword().v()))
				.collect(Collectors.toList());
	}

}
