package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

/**
 * @author nampt
 * activity 3
 *
 */
@Stateless
public class FormulaMasterDomainServiceImpl implements FormulaMasterDomainService{

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

	@Inject
	FormulaManualRepository formulaManualRepository;
	
	public void add(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader) {
		this.formulaMasterRepository.add(formulaMaster);
		this.formulaHistoryRepository.add(formulaHistory);
		if (formulaEasyHeader.getConditionAtr().value == 0) {
			this.formulaEasyHeaderRepository.add(formulaEasyHeader);
		}
	}

	public void update(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader,
			List<FormulaEasyCondition> formulaEasyCondition, FormulaEasyDetail formulaEasyDetail,
			List<FormulaEasyStandardItem> formulaEasyStandardItem, FormulaManual formulaManual) {
		this.formulaMasterRepository.update(formulaMaster);

		if (formulaMaster.getDifficultyAtr().value == 0) {
			this.formulaEasyConditionRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			this.formulaEasyConditionRepository.add(formulaEasyCondition);
			this.formulaEasyDetailRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			this.formulaEasyDetailRepository.add(formulaEasyDetail);
			this.formulaEasyStandardItemRepository.remove(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			this.formulaEasyStandardItemRepository.add(formulaEasyStandardItem);
		} else if(formulaMaster.getDifficultyAtr().value == 1){
			
			/**
			 * DB参照条件：
			 * @ CCD = login company code
			 * @ FORMULA_CD = [B_INP_ 001]
			 * History ID of the history selected with @HIST_ID = [A_LST_001]
			 */
			Optional<FormulaManual> optional = this.formulaManualRepository.findByPriKey(formulaMaster.getCompanyCode(), formulaMaster.getFormulaCode(),
					formulaHistory.getHistoryId());
			if(optional.isPresent() == false){
				this.formulaManualRepository.add(formulaManual);
			} else if (optional.isPresent() == true){
				this.formulaManualRepository.update(formulaManual);
			}
		}
	}

}
