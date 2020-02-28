package nts.uk.screen.hr.ws.databeforereflecting.retirementinformation;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.command.RetirementInformationRegisterCommand;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.command.RetirementInformationRegisterCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetiDateDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetirementInformationFinder;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;

@Path("databeforereflecting/")
@Produces(MediaType.APPLICATION_JSON)
public class RetirementInformationWebService {

	@Inject
	private RetirementInformationFinder finder;
	
	@Inject
	private RetirementInformationRegisterCommandHandler cmmd;

	@POST
	@Path("startPage")
	public RetiDateDto startPage() {
		return this.finder.startPage();
	}
	
	@POST
	@Path("search-retired")
	public SearchRetiredResultDto searchRetiredEmployees(SearchRetiredEmployeesQuery query) {
		return this.finder.searchRetiredEmployees(query);
	}
	
	@POST
	@Path("register-retired")
	public void registerRetiredEmployees(RetirementInformationRegisterCommand ccmd) {
		cmmd.handle(ccmd);
	}
	
}
