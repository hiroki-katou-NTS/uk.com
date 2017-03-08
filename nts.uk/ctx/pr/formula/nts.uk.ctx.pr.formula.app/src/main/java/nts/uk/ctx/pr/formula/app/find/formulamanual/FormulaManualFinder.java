package nts.uk.ctx.pr.formula.app.find.formulamanual;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaManualFinder {
	@Inject
	private FormulaManualRepository repository;

	/**
	 * @CCD = Login company code
	 * @FORMULA_CD = 【B_INP_001】
	 * @HIST_ID = History ID of the history selected with @HIST_ID = [A_LST_001]
	 * 
	 * @param formulaCode
	 * @param historyId
	 * @return
	 */
	public Optional<FormulaManualDto> findByPriKey(String formulaCode, String historyId) {

		LoginUserContext login = AppContexts.user();

		return this.repository.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
				.map(f -> FormulaManualDto.fromDomain(f));
	}

}
