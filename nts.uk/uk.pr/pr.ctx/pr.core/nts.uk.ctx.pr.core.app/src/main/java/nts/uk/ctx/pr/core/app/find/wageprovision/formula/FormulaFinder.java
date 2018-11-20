package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

}
