/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulamaster;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaMasterFinder {
	/**
	 * FormulaMasterRepository
	 */
	@Inject
	private FormulaMasterRepository repository;
	
	/**
	 * @return FormulaDto
	 */
	public List<FormulaDto> find() {
		
		LoginUserContext login =  AppContexts.user();
		
		return this.repository.findAll(login.companyCode()).stream().
				map(f -> {return FormulaDto.fromDomain(f);})
				.collect(Collectors.toList());
	}
}
