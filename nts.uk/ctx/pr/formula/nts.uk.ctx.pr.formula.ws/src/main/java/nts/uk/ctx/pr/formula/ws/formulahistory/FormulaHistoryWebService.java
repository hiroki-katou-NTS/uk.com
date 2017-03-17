package nts.uk.ctx.pr.formula.ws.formulahistory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.formula.app.find.formulahistory.FormulaHistoryDto;
import nts.uk.ctx.pr.formula.app.find.formulahistory.FormulaHistoryFinder;

@Path("pr/formula/formulaHistory")
@Produces("application/json")
public class FormulaHistoryWebService extends WebService {
	
	@Inject
	private FormulaHistoryFinder formulaHistoryFinder;
	
	@POST
	@Path("findFormulaHistoryByCode/{formulaCode}")
	public List<FormulaHistoryDto> findFormulaHistoryByCode(@PathParam("formulaCode") String formulaCode){
		return this.formulaHistoryFinder.findByFormulaCode(formulaCode);
	}
}
