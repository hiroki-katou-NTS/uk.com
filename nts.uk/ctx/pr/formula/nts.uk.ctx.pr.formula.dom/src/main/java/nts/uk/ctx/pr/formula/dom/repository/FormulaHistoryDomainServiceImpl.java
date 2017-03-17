package nts.uk.ctx.pr.formula.dom.repository;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;

@Stateless
public class FormulaHistoryDomainServiceImpl implements FormulaHistoryDomainService {

	@Inject
	private FormulaHistoryRepository formulaHistoryRepository;

	@Inject
	private FormulaEasyHeaderRepository formulaEasyHeaderRepository;

	public void add(FormulaHistory formulaHistoryAdd, FormulaEasyHeader formulaEasyHead,
			FormulaHistory formulaHistoryUpdate) {
		
		formulaHistoryRepository.add(formulaHistoryAdd);
		
		if (formulaEasyHead.getConditionAtr().value == 0) {
			formulaEasyHeaderRepository.add(formulaEasyHead);
		} else if (formulaEasyHead.getConditionAtr().value == 1) {
			formulaHistoryRepository.update(formulaHistoryUpdate);
		}
	}

}
