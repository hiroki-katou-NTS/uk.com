package nts.uk.ctx.sys.portal.ws.mypage;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.arc.layer.ws.WebService;
import nts.uk.shr.com.context.AppContexts;

@Path("/myprofile")
@Stateless
public class MyPageWebService extends WebService {
	@POST
	@Path("birthday")
	public MyBirthdayDto findMyBirthday() {
		String companyId = AppContexts.user().companyId();
		String employeeCode = AppContexts.user().employeeCode();
		
		return new MyBirthdayDto("日通　太郎さん　お誕生日おめでとうございます。", true);
	}
}
