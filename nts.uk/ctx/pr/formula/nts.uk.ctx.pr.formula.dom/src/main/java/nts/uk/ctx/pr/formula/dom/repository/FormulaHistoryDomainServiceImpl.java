package nts.uk.ctx.pr.formula.dom.repository;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;

/**
 * @author Nam-PT
 * K + J screen
 * activity 32, 33
 *
 */
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
	
	public void update(int conditionAtr, FormulaHistory formulaHistory, FormulaManual formulaManual){
		formulaHistoryRepository.remove(formulaHistory);
		
		
	}

}
