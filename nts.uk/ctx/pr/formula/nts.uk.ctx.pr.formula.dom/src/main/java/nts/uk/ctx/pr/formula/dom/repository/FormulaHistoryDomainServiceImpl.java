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

	public void add(int difficultyAtr, FormulaHistory formulaHistoryAdd, FormulaEasyHeader formulaEasyHead,
			FormulaHistory formulaHistoryUpdate,FormulaHistory previousFormulaHistoryUpdate) {

		this.formulaHistoryRepository.add(formulaHistoryAdd);

		if (difficultyAtr == 0) {
			this.formulaEasyHeaderRepository.add(formulaEasyHead);
			this.formulaHistoryRepository.update(formulaHistoryUpdate);
			this.formulaHistoryRepository.update(previousFormulaHistoryUpdate);
		} else if (difficultyAtr == 1) {
			this.formulaHistoryRepository.update(formulaHistoryUpdate);
			this.formulaHistoryRepository.update(previousFormulaHistoryUpdate);
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

			// select last history
			Optional<FormulaHistory> lastFormulaHistory = this.formulaHistoryRepository.findLastHistory(companyCode,
					new FormulaCode(formulaCode));
			// update history by historyId of last history, startDate =
			// startDate input
			FormulaHistory lastFormulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(formulaCode),
					lastFormulaHistory.get().getHistoryId(), new YearMonth(startDate),
					new YearMonth(lastFormulaHistory.get().getEndDate().v()));

			// select previous history with startDate
			Optional<FormulaHistory> previousFormulaHistory = this.formulaHistoryRepository
					.findPreviousHistory(companyCode, new FormulaCode(formulaCode), new YearMonth(startDate));
			// update previous history with endDate = startDate of last History
			FormulaHistory previousFormulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(formulaCode),
					previousFormulaHistory.get().getHistoryId(), new YearMonth(previousFormulaHistory.get().getStartDate().v()),
					new YearMonth(lastFormulaHistory.get().getStartDate().v()));

			this.formulaHistoryRepository.update(lastFormulaHistoryUpdate);
			this.formulaHistoryRepository.update(previousFormulaHistoryUpdate);
		}
	}

}
