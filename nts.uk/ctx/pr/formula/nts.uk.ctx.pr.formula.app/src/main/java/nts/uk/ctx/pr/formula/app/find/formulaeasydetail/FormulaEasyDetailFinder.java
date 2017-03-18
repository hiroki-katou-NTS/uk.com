/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasydetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaEasyDetailFinder {

	@Inject
	private FormulaEasyDetailRepository formulaEasyDetailRepository;
	// @Inject
	// private FormulaEasyAItemRepository formulaEasyAItemRepository;

	// public Optional<FormulaEasyDetailDto> find(String companyCode, String
	// formulaCode, String historyId, String easyFormulaCode) {
	// Optional<FormulaEasyDetail> formulaEasyDetail =
	// this.formulaEasyDetailRepository.find(companyCode, formulaCode,
	// historyId, easyFormulaCode);
	// List<ItemCode> lstItemCode =
	// formulaEasyAItemRepository.findAll(companyCode, formulaCode, historyId,
	// easyFormulaCode);
	// formulaEasyDetail.get().setAItemCode(lstItemCode);
	// return formulaEasyDetail.map(f -> FormulaEasyDetailDto.fromDomain(f));
	// return null;
	// }

//	public Optional<FormulaEasyDetailDto> findByPriKey(String formulaCode, String historyId, String easyFormulaCode) {
//
//		LoginUserContext login = AppContexts.user();
//
//		return formulaEasyDetailRepository.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId,
//				new EasyFormulaCode(easyFormulaCode)).map(f -> FormulaEasyDetailDto.fromDomain(f));
//	}

	public Optional<FormulaEasyDetailDto> findWithoutPriKey(String formulaCode, String historyId) {
//		LoginUserContext login = AppContexts.user();
//
//		return formulaEasyDetailRepository
//				.findWithOutPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
//				.map(f -> {return FormulaEasyDetailDto.fromDomain(f);});
		return null;
	}
}
