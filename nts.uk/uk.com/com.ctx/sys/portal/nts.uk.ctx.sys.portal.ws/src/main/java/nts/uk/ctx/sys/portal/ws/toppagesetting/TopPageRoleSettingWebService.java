package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.toppagesetting.AddTopPageRoleSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageRoleSettingCommandBase;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPageRoleSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageRoleSettingDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageRoleSettingFinder;
import nts.uk.ctx.sys.portal.app.find.webmenu.smartphonemenu.SPMenuFinder;

@Path("sys/portal/toppagesetting/roleset")
@Produces("application/json")
public class TopPageRoleSettingWebService {
	
	@Inject
	TopPageRoleSettingFinder topPageRoleSettingFinder;

	@Inject
	UpdateTopPageRoleSettingCommandHandler updateTopPageRoleSettingCommandHandler;
	
	@Inject
	AddTopPageRoleSettingCommandHandler addTopPageRoleSettingCommandHandler;
	
	@Inject
	private SPMenuFinder spMenuFinder;
	
	@POST
	@Path("findAll")
	public List<TopPageRoleSettingDto> findAll() {
		return this.topPageRoleSettingFinder.getAllByCompanyId();
	}

	@POST
	@Path("save")
	public void update(TopPageRoleSettingCommandBase command) {
		// ドメインモデル「権限別トップページ設定」を取得
		List<TopPageRoleSettingDto> dto = this.findAll();
		
		// ドメインモデル「標準メニュー」を取得する
//		List<StandardMenu> lstStandardMenu = this.spMenuFinder.getStandardMenu(AppContexts.user().companyId());
	}
}
