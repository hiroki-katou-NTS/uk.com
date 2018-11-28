package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class FormulaFinder {

    @Inject
    private FormulaService formulaService;

    @Inject
    private FormulaRepository formulaRepository;

    public List<FormulaDto> getFormulaByYearMonth(int yearMonth) {
        return formulaService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(FormulaDto::fromDomain).collect(Collectors.toList());
    }

    public FormulaDto getFormulaById(String formulaCode) {
        String cid = AppContexts.user().companyId();
        Optional<Formula> domainOtp = formulaRepository.getFormulaById(cid, formulaCode);
        return domainOtp.map(FormulaDto::fromDomain).orElse(null);
    }
}
