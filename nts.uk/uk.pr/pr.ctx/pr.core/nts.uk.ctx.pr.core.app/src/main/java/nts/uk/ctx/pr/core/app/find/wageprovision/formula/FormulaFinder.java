package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

<<<<<<< HEAD
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;

import java.util.ArrayList;
=======
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
>>>>>>> pj/pr/team_G/QMM019
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

<<<<<<< HEAD
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 計算式: Finder
*/
public class FormulaFinder {

    @Inject
    private FormulaRepository formulaRepository;

    public List<FormulaDto> getAllFormula(){
        return formulaRepository.getAllFormula().stream().map(FormulaDto::fromDomainToDto).collect(Collectors.toList());
    }

    public List<FormulaDto> getAllFormulaAndHistory(){
        return formulaRepository.getAllFormula().stream().map(item -> {
            FormulaDto dto = FormulaDto.fromDomainToDto(item);
            formulaRepository.getFormulaHistoryByCode(item.getFormulaCode().v()).ifPresent(formulaHistory -> {
                dto.setFormulaHistory(formulaHistory.getHistory());
            });
            return dto;
        }).collect(Collectors.toList());
    }

=======
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
>>>>>>> pj/pr/team_G/QMM019
}
