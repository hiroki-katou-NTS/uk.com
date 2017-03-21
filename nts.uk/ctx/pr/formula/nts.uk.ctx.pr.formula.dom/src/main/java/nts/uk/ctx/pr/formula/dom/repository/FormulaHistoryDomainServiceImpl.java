package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;

/**
 * @author Nam-PT K screen activity 33
 *
 */
@Stateless
public class FormulaHistoryDomainServiceImpl implements FormulaHistoryDomainService {

	@Inject
	private FormulaHistoryRepository formulaHistoryRepository;

	@Inject
	private FormulaEasyHeaderRepository formulaEasyHeaderRepository;

	@Inject
	private FormulaManualRepository formulaManualRepository;

	@Inject
	private FormulaEasyConditionRepository formulaEasyConditionRepository;

	@Inject
	private FormulaEasyDetailRepository formulaEasyDetailRepository;

	@Inject
	private FormulaEasyStandardItemRepository formulaEasyStandardItemRepository;

	@Inject
	private FormulaMasterRepository formulaMasterRepository;

	public void add(FormulaHistory formulaHistoryAdd, FormulaEasyHeader formulaEasyHead,
			FormulaHistory formulaHistoryUpdate) {

		this.formulaHistoryRepository.add(formulaHistoryAdd);

		if (formulaEasyHead.getConditionAtr().value == 0) {
			this.formulaEasyHeaderRepository.add(formulaEasyHead);
		} else if (formulaEasyHead.getConditionAtr().value == 1) {
			this.formulaHistoryRepository.update(formulaHistoryUpdate);
		}
	}

	public void remove(int difficultyAtr, String companyCode, String formulaCode, String historyId, int startDate) {
		this.formulaHistoryRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);

		if (difficultyAtr == 0) {
			this.formulaManualRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);
		} else if (difficultyAtr == 1) {
			this.formulaEasyHeaderRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);
			this.formulaEasyConditionRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);
			this.formulaEasyDetailRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);
			this.formulaEasyStandardItemRepository.remove(companyCode, new FormulaCode(formulaCode), historyId);
		}

		if (this.formulaHistoryRepository.isNewestHistory(companyCode, new FormulaCode(formulaCode),
				new YearMonth(startDate)) == false) {
			this.formulaMasterRepository.remove(companyCode, new FormulaCode(formulaCode));
		} else if (this.formulaHistoryRepository.isNewestHistory(companyCode, new FormulaCode(formulaCode),
				new YearMonth(startDate)) == true) {
			Optional<FormulaHistory> formulaHistory = this.formulaHistoryRepository.findLastHistory(companyCode,
					new FormulaCode(formulaCode));
			FormulaHistory formulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(formulaCode),
					formulaHistory.get().getHistoryId(), new YearMonth(startDate),
					new YearMonth(formulaHistory.get().getEndDate().v()));
			this.formulaHistoryRepository.update(formulaHistoryUpdate);
		}
	}

}
