package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.app.find.formulahistory.FormulaHistoryDto;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class FormulaItemSelectFinder {

	@Inject
	private FormulaHistoryRepository formulaHistoryRepository;

	@Inject
	private FormulaMasterRepository formulaMasterRepository;

	public List<FormulaItemSelectDto> init(String formulaCode, int baseDate) {

		LoginUserContext login = AppContexts.user();
		String companyCode = login.companyCode();

		List<FormulaHistoryDto> formulaHistoryDto = formulaHistoryRepository
				.findDataDifFormulaCode(companyCode, new FormulaCode(formulaCode), new YearMonth(baseDate)).stream()
				.map(f -> FormulaHistoryDto.fromDomain(f)).collect(Collectors.toList());
		
		List<FormulaItemSelectDto> formulaDto = formulaMasterRepository.findByCompanyCodeAndFormulaCodes(companyCode, formulaHistoryDto.stream().map(item -> {
			return new FormulaCode(item.getFormulaCode());
		}).collect(Collectors.toList())).stream().map(f -> FormulaItemSelectDto.fromDomain(f)).collect(Collectors.toList());
		
		return formulaDto;
	}

}
