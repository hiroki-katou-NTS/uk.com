package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

/**
 * @author nampt
 *
 */
@Stateless
public class FormulaMasterDomainService {

	@Inject
	FormulaMasterRepository formulaMasterRepository;

	@Inject
	FormulaHistoryRepository formulaHistoryRepository;

	@Inject
	FormulaEasyHeaderRepository formulaEasyHeaderRepository;

	@Inject
	FormulaEasyConditionRepository formulaEasyConditionRepository;

	@Inject
	FormulaEasyDetailRepository formulaEasyDetailRepository;

	@Inject
	FormulaEasyStandardItemRepository formulaEasyStandardItemRepository;

	public void add(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader) {
		formulaMasterRepository.add(formulaMaster);
		formulaHistoryRepository.add(formulaHistory);
		if (formulaEasyHeader.getConditionAtr().value == 0) {
			formulaEasyHeaderRepository.add(formulaEasyHeader);
		}
	}

	public void update(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader,
			List<FormulaEasyCondition> formulaEasyCondition, FormulaEasyDetail formulaEasyDetail,
			List<FormulaEasyStandardItem> formulaEasyStandardItem) {
		formulaMasterRepository.update(formulaMaster);

		if (formulaMaster.getDifficultyAtr().value == 0) {
			formulaEasyConditionRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			formulaEasyConditionRepository.add(formulaEasyCondition);
			formulaEasyDetailRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			formulaEasyDetailRepository.add(formulaEasyDetail);
			formulaEasyStandardItemRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			formulaEasyStandardItemRepository.add(formulaEasyStandardItem);
		} else if(formulaMaster.getDifficultyAtr().value == 1){
			
		}
	}

}
