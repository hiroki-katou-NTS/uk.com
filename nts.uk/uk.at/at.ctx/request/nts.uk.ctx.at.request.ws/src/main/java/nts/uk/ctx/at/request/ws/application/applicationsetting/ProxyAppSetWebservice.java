package nts.uk.ctx.at.request.ws.application.applicationsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.ProxyAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.UpdateProxyAppSetCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.company.applicationsetting.ProxyAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationsetting.ProxyAppSetFinder;

@Path("at/request/application/setting/proxy")
@Produces("application/json")
public class ProxyAppSetWebservice extends WebService{
	@Inject
	private ProxyAppSetFinder proxyFinder;
	@Inject
	private UpdateProxyAppSetCommandHandler update;
	/**
	 * get all proxy app set
	 * @return
	 * @author yennth
	 */
	@POST
	@Path("findAll")
	public List<ProxyAppSetDto> getAll(){
		 return proxyFinder.findAll();
	}
	@POST
	@Path("findApp/{closureId}")
	public ProxyAppSetDto getByCom(@PathParam("closureId") int appType){
		 return proxyFinder.findByApp(appType);
	}
	
	@POST
	@Path("update")
	public void update(ProxyAppSetCommand command){
		 this.update.handle(command);
	}
}
