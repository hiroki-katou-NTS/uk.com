package nts.uk.ctx.at.schedule.ws.shift.specificdayset.company;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.CompanySpecificDateCommand;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.DeleteCompanySpecificDateCommand;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.DeleteCompanySpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.InsertCompanySpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.UpdateCompanySpecificDateCommand;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company.UpdateCompanySpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company.CompanySpecificDateDto;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company.CompanySpecificDateFinder;

@Path("at/schedule/shift/specificdayset/company")
@Produces("application/json")
public class CompanySpecificDateWebService extends WebService {

	@Inject
	private CompanySpecificDateFinder find;
	@Inject 
	private InsertCompanySpecificDateCommandHandler insertCommnad;
	@Inject 
	private UpdateCompanySpecificDateCommandHandler updateCommand;
	@Inject 
	private DeleteCompanySpecificDateCommandHandler deleteCommand;

	@POST
	@Path("getcompanyspecificdaysetbydate")
	public Optional<CompanySpecificDateDto> getCompanySpecificDateByCompany(String processDate) {
		return this.find.getComSpecByDate(processDate);
	}

	@POST
	@Path("getcompanyspecificdaysetbydatewithname")
	public List<CompanySpecificDateDto> getCompanySpecificDateByCompanyWithName(String processDate) {
		return this.find.getComSpecByDateWithName(processDate);
	}
	
	@POST
	@Path("insertcompanyspecificdate")
	public void InsertCompanySpecificDate(List<CompanySpecificDateCommand> lstComSpecificDateItem) {
		this.insertCommnad.handle(lstComSpecificDateItem);
	}
	
	@POST
	@Path("updatecompanyspecificdate")
	public void UpdateCompanySpecificDate(List<UpdateCompanySpecificDateCommand> lstUpdComSpecificDateItem) {
		this.updateCommand.handle(lstUpdComSpecificDateItem);
	}
	
	@POST
	@Path("deletecompanyspecificdate")
	public void DeleteCompanySpecificDate(DeleteCompanySpecificDateCommand deleteCommand) {
		this.deleteCommand.handle(deleteCommand);
	}
	
	
	
}