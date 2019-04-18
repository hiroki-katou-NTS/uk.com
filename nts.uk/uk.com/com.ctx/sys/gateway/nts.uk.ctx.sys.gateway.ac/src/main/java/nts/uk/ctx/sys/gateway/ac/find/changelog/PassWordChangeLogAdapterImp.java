package nts.uk.ctx.sys.gateway.ac.find.changelog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.changelog.PassWordChangeLogPub;
import nts.uk.ctx.sys.gateway.dom.adapter.changelog.PassWordChangeLogAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.changelog.PassWordChangeLogImport;

@Stateless
public class PassWordChangeLogAdapterImp implements PassWordChangeLogAdapter{

	@Inject
	private PassWordChangeLogPub pwChangeLogPub;
	
	@Override
	public List<PassWordChangeLogImport> getListPwChangeLog(String userId) {
		return pwChangeLogPub.getListPwChangeLog(userId).stream()
					.map(c -> new PassWordChangeLogImport(c.getLogID(),
							c.getUserID(),
							c.getModifiedDate(),
							c.getPassword()))
					.collect(Collectors.toList());
	}

}
