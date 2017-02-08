/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasydetail;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyAItemRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaEasyDetailFinder {

	@Inject
	private FormulaEasyDetailRepository formulaEasyDetailRepository;
	@Inject
	private FormulaEasyAItemRepository formulaEasyAItemRepository;

	public Optional<FormulaEasyDetailDto> find(String companyCode, String formulaCode, String historyId,
			String easyFormulaCode) {
		Optional<FormulaEasyDetail> formulaEasyDetail = this.formulaEasyDetailRepository.find(companyCode, formulaCode,
				historyId, easyFormulaCode);
		List<ItemCode> lstItemCode = formulaEasyAItemRepository.findAll(companyCode, formulaCode, historyId,
				easyFormulaCode);
		formulaEasyDetail.get().setAItemCode(lstItemCode);
		return formulaEasyDetail.map(f -> FormulaEasyDetailDto.fromDomain(f));
	}
}
