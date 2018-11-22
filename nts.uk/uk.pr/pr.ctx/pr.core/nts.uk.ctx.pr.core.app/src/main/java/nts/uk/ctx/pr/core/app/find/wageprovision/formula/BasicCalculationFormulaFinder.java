package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationFormulaRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* かんたん計算式: Finder
*/
public class BasicCalculationFormulaFinder
{

    @Inject
    private BasicCalculationFormulaRepository basicCalculationFormulaRepository;

    public List<BasicCalculationFormulaDto> getBasicCalculationFormulaByHistoryID(String historyID){
        return basicCalculationFormulaRepository.getBasicCalculationFormulaByHistoryID(historyID).stream().map(BasicCalculationFormulaDto::fromDomainToDto)
                .collect(Collectors.toList());
    }

}
