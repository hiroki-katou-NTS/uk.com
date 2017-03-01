package nts.uk.ctx.pr.formula.app.find.formulahistory;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class FormulaHistoryFinder {

	@Inject
	FormulaHistoryRepository formulaHistoryRepository;

	public List<FormulaHistoryDto> find() {

		LoginUserContext login = AppContexts.user();

		return this.formulaHistoryRepository.findAll(login.companyCode()).stream().map(f -> {
			return FormulaHistoryDto.fromDomain(f);
		}).collect(Collectors.toList());

	}

}
