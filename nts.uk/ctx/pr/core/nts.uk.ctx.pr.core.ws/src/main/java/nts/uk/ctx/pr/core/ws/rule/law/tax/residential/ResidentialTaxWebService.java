package nts.uk.ctx.pr.core.ws.rule.law.tax.residential;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.AddResidentialTaxCommand;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.AddResidentialTaxCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.DeleteResidentialTaxCommand;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.DeleteResidentialTaxCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.UpdateResidentialTaxCommand;
import nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.UpdateResidentialTaxCommandHandler;
import nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.ResidentialTaxDto;
import nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.ResidentialTaxFinder;

@Path("pr/core/residential")
@Produces("application/json")
public class ResidentialTaxWebService extends WebService {
	@Inject
	private AddResidentialTaxCommandHandler addData;
	@Inject
	private DeleteResidentialTaxCommandHandler deleleData;
	@Inject
	private UpdateResidentialTaxCommandHandler updateData;
	@Inject
	private ResidentialTaxFinder finder;

	@POST
	@Path("findallresidential")
	public List<ResidentialTaxDto> getAllResidential() {
//		 this.finder.getAllResidentialTax()
//			.stream().collect(Collectors.groupingBy(e -> e.getPrefectureCode(), Collectors.toList()));
		return this.finder.getAllResidentialTax();
	}

	@POST
	@Path("addresidential")
	public void addResidential(AddResidentialTaxCommand command) {
		this.addData.handle(command);
	}

	@POST
	@Path("updateresidential")
	public void updateResidential(UpdateResidentialTaxCommand command) {
		this.updateData.handle(command);
	}
	@POST
	@Path("deleteresidential")
	public void addResidential(DeleteResidentialTaxCommand command) {
		this.deleleData.handle(command);
	}
	@POST
	@Path("findallresidential/resiTaxCode")
	public List<ResidentialTaxDto> getAllResidential(String resiTaxCode,  String  resiTaxReportCode) {
		return this.finder.getAllResidentialTax();
	}
}
