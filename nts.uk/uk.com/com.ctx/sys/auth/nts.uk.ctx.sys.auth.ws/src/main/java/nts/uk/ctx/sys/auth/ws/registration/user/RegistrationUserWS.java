package nts.uk.ctx.sys.auth.ws.registration.user;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.find.registration.user.CompanyImportDto;
import nts.uk.ctx.sys.auth.app.find.registration.user.RegistrationUserFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserDto;
import nts.uk.ctx.sys.auth.app.find.user.UserKeyDto;

@Path("ctx/sys/auth/regis/user")
@Produces("application/json")
public class RegistrationUserWS extends WebService {
	
	@Inject
	private RegistrationUserFinder registrationUserFinder;
	
	@POST
	@Path("findAllCom")
	public List<CompanyImportDto> findCompanyImportList() {
		return this.registrationUserFinder.getCompanyImportList();
	}
	
	@POST
	@Path("getlistUser")
	public List<UserDto> getListUser() {
//		return this.registrationUserFinder.;
		return null;
	}
	
	@POST
	@Path("findByKey")
	public List<UserDto> FindByKey(UserKeyDto userKeyDto) {
//		return this.registrationUserFinder.;
		return null;
	}

}
