package nts.uk.ctx.sys.portal.ws.toppagesetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.find.layout.LayoutDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.DisplayMyPageFinder;
/**
 * 
 * @author hoatt
 *
 */
@Path("topageselfsetting")
public class TopPageSettingWebService extends WebService {

	@Inject
	private DisplayMyPageFinder myPage;
	
	@POST
	@Path("getmypage")
	public LayoutDto activeLayoutSetting(String layoutID) {
		return myPage.findLayout(layoutID);
	}
}
