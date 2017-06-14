package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommand;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageSelfSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.SelectMyPageDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSetSelectedFinder;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageSelfSettingFinder;

/**
 * The Class MyPageWebService.
 */
@Path("topageselfsetting")
@Stateless
public class TopPageSelfWebService extends WebService {
	@Inject
	private TopPageSelfSettingFinder topPageSelfSettingFinder;
	@Inject
	private TopPageSelfSettingCommandHandler saveCommandHandler;
	@Inject 
	private TopPageSelfSetSelectedFinder getTopPageSelfSet;
	
	@POST
	@Path("/select")
	public List<SelectMyPageDto> findSelectMyPage() {
		return topPageSelfSettingFinder.findSelectMyPage();
	}
	
	@POST
	@Path("/add")
	public void saveSelfSetting(TopPageSelfSettingCommand comamnd) {
		this.saveCommandHandler.handle(comamnd);
	}
	
	@POST
	@Path("/finditemselected")
	public Optional<TopPageSelfSettingDto> getTopPageSelfSet(){
		return getTopPageSelfSet.getTopPageSelfSet();
	}
}
