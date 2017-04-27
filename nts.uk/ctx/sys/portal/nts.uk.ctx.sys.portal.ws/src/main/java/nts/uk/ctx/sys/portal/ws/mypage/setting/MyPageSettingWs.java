package nts.uk.ctx.sys.portal.ws.mypage.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.mypage.setting.command.UpdateMyPageSettingCommand;
import nts.uk.ctx.sys.portal.app.mypage.setting.command.UpdateMyPageSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.mypage.setting.find.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.mypage.setting.find.MyPageSettingFinder;

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
	@Path("myPageSetting/{companyId}")
	public MyPageSettingDto getMyPageSettingDetail(@PathParam("companyId") String companyId) {
		return myPageSettingFinder.findByCompanyId(companyId);
	}

	/**
	 * Update my page setting.
	 *
	 * @param command the command
	 */
	@POST
	@Path("myPageSetting/update")
	public void updateMyPageSetting(UpdateMyPageSettingCommand command) {
		myPageSettingCommandHandler.handle(command);
	}
}
