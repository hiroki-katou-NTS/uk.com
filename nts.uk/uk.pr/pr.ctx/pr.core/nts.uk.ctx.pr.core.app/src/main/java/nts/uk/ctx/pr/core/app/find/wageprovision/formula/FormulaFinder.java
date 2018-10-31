package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FormulaFinder {

    @Inject
    private FormulaService formulaService;

    public List<FormulaDto> getFormulaByYearMonth(int yearMonth) {
        return formulaService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(x -> FormulaDto.fromDomain(x)).collect(Collectors.toList());
    }

}
