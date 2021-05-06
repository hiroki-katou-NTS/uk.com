package nts.uk.ctx.at.aggregation.ws.schedulecounter.initcompanyinfo;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.initcompanyinfo.CompanyInfoRegisterCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.initcompanyinfo.CompanyInfoRegisterCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.personalcounter.RegisterPersonalCounterCommand;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.initcompanyinfo.CompanyInfoFinder;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.initcompanyinfo.EsimatedInfoDto;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.initcompanyinfo.InitInfoDto;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;

/**
 * KML002 H
 * @author hoangnd
 *
 */

@Path("ctx/at/schedule/budget/initCompanyInfo")
@Produces("application/json")
public class InitCompanyInfoWebService extends WebService {
	
	@Inject
	private CompanyInfoFinder companyInfoFinder;
	
	@Inject
	private CompanyInfoRegisterCommandHandler registerHandler;
	
	@Path("init")
	@POST
	public InitInfoDto init() {
		
		return companyInfoFinder.getInitInfo();
	}
	
	@Path("selectedTab")
	@POST
	public EsimatedInfoDto selectedTab() {
		
		return companyInfoFinder.getEstimatedInfo();
	}
	
	@Path("register")
	@POST
	public void register(CompanyInfoRegisterCommand command) {
		
		registerHandler.handle(command);
	}
	
}
