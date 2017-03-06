package nts.uk.ctx.pr.core.ws.rule.law.tax.residential.input;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.input.PersonResiTaxDto;
import nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.input.PersonResiTaxFinder;

@Path("pr/core/rule/law/tax/residential/input")
@Produces("application/json")
public class Qmm025WebService extends WebService {
	@Inject
	private PersonResiTaxFinder personResiTaxFinder;
	
	@POST
	@Path("findAll/{yearKey}")
	public List<PersonResiTaxDto> findAll(@PathParam("yearKey") int yearKey){
		return this.personResiTaxFinder.findAll(yearKey);
	}
}
