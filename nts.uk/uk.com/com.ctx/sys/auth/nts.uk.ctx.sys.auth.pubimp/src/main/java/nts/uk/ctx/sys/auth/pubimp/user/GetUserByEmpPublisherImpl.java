package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;

@Stateless
public class GetUserByEmpPublisherImpl {

	
	@Inject
	private GetUserByEmpFinder getUserByEmpFinder;
	
	
	public List<UserAuthDto> getByListEmp(List<String> listEmpID) {
		
		return null;
	}
		
}
