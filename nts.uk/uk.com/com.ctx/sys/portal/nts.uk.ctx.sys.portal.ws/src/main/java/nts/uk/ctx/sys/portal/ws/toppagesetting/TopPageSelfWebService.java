package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommand;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.DisplayMyPageFinder;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.LayoutAllDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.SelectMyPageDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingFinder;

/**
 * @author hoatt
 */
@Path("topageselfsetting")
@Produces("application/json")
public class TopPageSelfWebService extends WebService {
	@Inject
	private TopPageSelfSettingFinder topPageSelfSettingFinder;
	@Inject
	private TopPageSelfSettingCommandHandler saveCommandHandler;
	@Inject
	private DisplayMyPageFinder topPage;
	/**
	 * Lay du lieu tu domain トップページ 
	 * @return
	 */
	@POST
	@Path("/select")
	public List<SelectMyPageDto> findSelectMyPage() {
		return topPageSelfSettingFinder.findSelectMyPage();
	}
	/**
	 * Xu ly dang ky top page self set
	 * @param comamnd
	 */
	@POST
	@Path("/save")
	public void saveSelfSetting(TopPageSelfSettingCommand comamnd) {
		this.saveCommandHandler.handle(comamnd);
	}
	/**
	 * Lay du lieu tu domain 本人トップページ設定 
	 * @return
	 */
	@POST
	@Path("/finditemselected")
	public TopPageSelfSettingDto getTopPageSelfSet(){
		return topPageSelfSettingFinder.getTopPageSelfSet();
	}
	/**
	 * get my page
	 * @param layoutID
	 * @return
	 */
//	@POST
//	@Path("getmypage")
//	public DefaultTopPageSetFactory.java activeLayoutSetting(String layoutID) {
//		return myPage.findLayout(layoutID);
//	}
	@POST
	@Path("gettoppage")
	public LayoutAllDto activeLayoutTopPage(String topPageCode){
		return topPage.findLayoutTopPage(topPageCode);
	}
}
