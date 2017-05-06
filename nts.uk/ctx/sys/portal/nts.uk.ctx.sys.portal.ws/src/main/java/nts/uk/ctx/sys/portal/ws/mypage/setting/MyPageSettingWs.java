package nts.uk.ctx.sys.portal.ws.mypage.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.mypage.setting.UpdateMyPageSettingCommand;
import nts.uk.ctx.sys.portal.app.command.mypage.setting.UpdateMyPageSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingFinder;

/**
 * The Class MyPageSettingWs.
 */
@Path("/mypage")
@Stateless
public class MyPageSettingWs extends WebService {

	/** The my page setting finder. */
	@Inject
	MyPageSettingFinder myPageSettingFinder;

	/** The my page setting command handler. */
	@Inject
	UpdateMyPageSettingCommandHandler myPageSettingCommandHandler;

	/**
	 * Gets the my page setting detail.
	 *
	 * @param companyId the company id
	 * @return the my page setting detail
	 */
	@POST
	@Path("getMyPageSetting/{companyId}")
	public MyPageSettingDto getMyPageSettingDetail(@PathParam("companyId") String companyId) {
		return myPageSettingFinder.findByCompanyId(companyId);
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
