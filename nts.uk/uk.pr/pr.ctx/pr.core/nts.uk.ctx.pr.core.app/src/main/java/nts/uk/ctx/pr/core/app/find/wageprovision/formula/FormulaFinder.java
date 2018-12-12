package nts.uk.ctx.pr.core.app.find.wageprovision.formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import java.util.List;
import java.util.stream.Collectors;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 計算式: Finder
*/
public class FormulaFinder {

    @Inject
    private FormulaRepository formulaRepository;

    @Inject
    private FormulaService formulaService;

    public List<FormulaDto> getAllFormula() {
        return formulaRepository.getAllFormula().stream().map(FormulaDto::fromDomainToDto).collect(Collectors.toList());
    }

    public List<FormulaDto> getAllFormulaAndHistory() {
        return formulaRepository.getAllFormula().stream().map(item -> {
            FormulaDto dto = FormulaDto.fromDomainToDto(item);
            formulaRepository.getFormulaHistoryByCode(item.getFormulaCode().v()).ifPresent(formulaHistory -> {
                dto.setFormulaHistory(formulaHistory.getHistory());
            });
            return dto;
        }).collect(Collectors.toList());
    }
    public List<FormulaDto> getFormulaByYearMonth(int yearMonth) {
        return formulaService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(FormulaDto::fromDomainToDto).collect(Collectors.toList());
    }

    public FormulaDto getFormulaById(String formulaCode) {
        return formulaRepository.getFormulaById(formulaCode).map(FormulaDto::fromDomainToDto).orElse(null);
    }

    public List<MasterUseDto> getMasterUseInfo (int masterUseClassification) {
        return formulaService.getMasterUseInfo(masterUseClassification);
    }


}

