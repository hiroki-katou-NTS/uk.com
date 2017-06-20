package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommand;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.SelectMyPageDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingFinder;

/**
 * @author hoatt
 */
@Path("topageselfsetting")
public class TopPageSelfWebService extends WebService {
	@Inject
	private TopPageSelfSettingFinder topPageSelfSettingFinder;
	@Inject
	private TopPageSelfSettingCommandHandler saveCommandHandler;
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
}
