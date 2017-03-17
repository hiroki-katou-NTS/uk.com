package nts.uk.ctx.pr.formula.ws.formulamaster;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.formula.app.find.formulamaster.FormulaDto;
import nts.uk.ctx.pr.formula.app.find.formulamaster.FormulaMasterFinder;

@Path("pr/formula/formulaMaster")
@Produces("application/json")
public class FormulaMasterWebService extends WebService {
	
	@Inject
	private FormulaMasterFinder formulaMasterFinder;

	@POST
	@Path("getAllFormula")
	public List<FormulaDto> getAllFormula(){
		return this.formulaMasterFinder.find();
	}
	
	@POST
	@Path("findFormula/{formulaCode}")
	public Optional<FormulaDto> findFormula(@PathParam("formulaCode") String formulaCode){
		return this.formulaMasterFinder.findByCompanyCodeAndFormulaCode(formulaCode);
	}
}
