package nts.uk.ctx.sys.portal.ws.mypage.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.mypage.setting.UpdateMyPageSettingCommand;
import nts.uk.ctx.sys.portal.app.command.mypage.setting.UpdateMyPageSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MyPageSettingWs.
 */
@Path("/mypage")
@Stateless
public class MyPageSetWebService extends WebService {

	public final String defaultCompanyId = "0";
	
	/** The my page setting finder. */
	@Inject
	MyPageSettingFinder myPageSettingFinder;

	/** The my page setting command handler. */
	@Inject
	UpdateMyPageSettingCommandHandler myPageSettingCommandHandler;

	/**
	 * Gets the my page setting detail.
	 *
	 * @return the my page setting detail
	 */
	@POST
	@Path("getMyPageSetting")
	public MyPageSettingDto getMyPageSettingDetail() {
		String companyId = AppContexts.user().companyId();
		return myPageSettingFinder.findByCompanyId(companyId);
	}
	
	@POST
	@Path("getDefaultMyPageSetting")
	public MyPageSettingDto getDefaultMyPageSettingDetail() {
		return myPageSettingFinder.findByCompanyId(defaultCompanyId);
	}
	
	/**
	 * Update my page setting.
	 *
	 * @param command the command
	 */
	@POST
	@Path("updateMyPageSetting")
	public void updateMyPageSetting(UpdateMyPageSettingCommand command) {
		myPageSettingCommandHandler.handle(command);
	}
}
