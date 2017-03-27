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
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;

/**
 * @author nampt activity 3
 *
 */
@Stateless
public class FormulaMasterDomainServiceImpl implements FormulaMasterDomainService {

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
		if (formulaMaster.getDifficultyAtr().value == 0) {
			this.formulaEasyHeaderRepository.add(formulaEasyHeader);
		}
	}

	public void update(int difficultyAtr, String companyCode, FormulaCode formulaCode, String historyId,
			List<FormulaEasyCondition> listFormulaEasyCondition, List<FormulaEasyDetail> listFormulaEasyDetail,
			List<FormulaEasyStandardItem> listFormulaEasyStandardItem, FormulaManual formulaManual) {
		// [計算式マスタ.UPD-1]を実施する
		this.formulaMasterRepository.update(companyCode, formulaCode);

		if (difficultyAtr == 0) {
			// [かんたん計算_条件.DEL-1]を実施する
			this.formulaEasyConditionRepository.remove(companyCode, formulaCode, historyId);
			// [かんたん計算_条件.INS-1]を実施する
			this.formulaEasyConditionRepository.add(listFormulaEasyCondition);
			// [かんたん計算_計算式.DEL-1]を実施する
			this.formulaEasyDetailRepository.remove(companyCode, formulaCode, historyId);
			// [かんたん計算_計算式.INS-1]を実施する
			listFormulaEasyDetail.forEach(item -> {
				this.formulaEasyDetailRepository.add(item);
			});
			// [かんたん計算_基準項目.DEL-1]を実施する
			this.formulaEasyStandardItemRepository.remove(companyCode, formulaCode, historyId);
			// [かんたん計算_基準項目.INS-1]を実施する
			this.formulaEasyStandardItemRepository.add(listFormulaEasyStandardItem);
		} else if (difficultyAtr == 1) {

			/**
			 * [詳細計算式マスタ.SEL-1]を実施する
			 * DB参照条件： @ CCD = login company code @ FORMULA_CD = [B_INP_ 001]
			 * History ID of the history selected with @HIST_ID = [A_LST_001]
			 */
			Optional<FormulaManual> optional = this.formulaManualRepository.findByPriKey(companyCode, formulaCode,
					historyId);
			if (optional.isPresent() == false) {
				//[詳細計算式マスタ.INS-1]を実施する
				this.formulaManualRepository.add(formulaManual);
			} else if (optional.isPresent() == true) {
				//[詳細計算式マスタ.UPD-1]を実施する
				this.formulaManualRepository.update(formulaManual);
			}
		}
	}

}
