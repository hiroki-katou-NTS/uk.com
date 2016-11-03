package nts.uk.ctx.core.ws.company;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.app.company.command.AddCompanyCommand;
import nts.uk.ctx.core.app.company.command.AddCompanyCommandHandler;
import nts.uk.ctx.core.app.company.command.RemoveCompanyCommand;
import nts.uk.ctx.core.app.company.command.RemoveCompanyCommandHandler;
import nts.uk.ctx.core.app.company.command.UpdateCompanyCommand;
import nts.uk.ctx.core.app.company.command.UpdateCompanyCommandHandler;
import nts.uk.ctx.core.app.company.find.CompanyDto;
import nts.uk.ctx.core.app.company.find.CompanyFinder;

@Path("/ctx/core/company")
@Produces("application/json")
public class CompanyWebService extends WebService {

	@Inject
	private CompanyFinder finder;
	
	@Inject
	private AddCompanyCommandHandler add;
	
	@Inject
	private UpdateCompanyCommandHandler update;
	
	@Inject
	private RemoveCompanyCommandHandler remove;
	
	@GET
	@Path("test")
	public CompanyDto test() {
		return new CompanyDto("001", "test");
	}
	
	@POST
	@Path("find/{code}")
	public CompanyDto find(@PathParam("code") String companyCode) {
		return this.finder.find(companyCode);
	}
	
	@POST
	@Path("findall")
	public List<CompanyDto> findAll() {
		return this.finder.findAll();
	}

	@POST
	@Path("add")
	public void add(AddCompanyCommand command) {
		this.add.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateCompanyCommand command) {
		this.update.handle(command);
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveCompanyCommand command) {
		this.remove.handle(command);
	}
	
}
