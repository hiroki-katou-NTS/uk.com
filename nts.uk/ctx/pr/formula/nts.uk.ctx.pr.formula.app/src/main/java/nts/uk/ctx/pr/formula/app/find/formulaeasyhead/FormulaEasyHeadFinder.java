package nts.uk.ctx.pr.formula.app.find.formulaeasyhead;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaEasyHeadFinder {
	@Inject
	private FormulaEasyHeaderRepository repository;

	Optional<FormulaEasyHeadDto> find(String companyCode, String formulaCode, String historyId,
			String referenceMasterNo) {
		return repository.find(companyCode, formulaCode, historyId, referenceMasterNo)
				.map(f -> FormulaEasyHeadDto.fromDomain(f));
	}
}
