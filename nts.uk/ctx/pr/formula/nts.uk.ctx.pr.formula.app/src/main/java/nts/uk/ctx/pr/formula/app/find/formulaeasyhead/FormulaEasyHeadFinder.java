package nts.uk.ctx.pr.formula.app.find.formulaeasyhead;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
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
			BigDecimal referenceMasterNo) {
		return repository.findByPriKey(companyCode, new FormulaCode(formulaCode), historyId, ReferenceMasterNo.valueOf(referenceMasterNo.toString()))
				.map(f -> FormulaEasyHeadDto.fromDomain(f));
	}
}
