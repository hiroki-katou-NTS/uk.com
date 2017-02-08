package nts.uk.ctx.pr.formula.app.find.formulamanual;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaManualFinder {
	@Inject
	private FormulaManualRepository repository;

	public Optional<FormulaManualDto> find(String companyCode, String formulaCode, String historyId) {
		return this.repository.find(companyCode, formulaCode, historyId).map(f -> FormulaManualDto.fromDomain(f));
	}

}
