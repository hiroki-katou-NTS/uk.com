package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSettingRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula.DetailFormulaCalculationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 詳細計算式: Finder
*/
public class DetailFormulaSettingFinder
{

    @Inject
    private DetailFormulaSettingRepository detailFormulaSettingRepository;

    @Inject
    private DetailFormulaCalculationService detailFormulaCalculationService;

    public DetailFormulaSettingDto getDetailFormulaSettingByHistoryID(String historyID){
        return detailFormulaSettingRepository.getDetailFormulaSettingById(historyID).map(DetailFormulaSettingDto::fromDomainToDto).orElse(null);
    }

    public Map<String, String> getEmbeddedFormulaContent(DetailFormulaConverterDto detailFormulaConverterDto){
        Map<String, String> formulaElements = detailFormulaConverterDto.getFormulaElements();
        for(Map.Entry formulaElement : formulaElements.entrySet()) {
            formulaElements.put(formulaElement.getKey().toString(), detailFormulaCalculationService.getEmbeddedFormulaDisplayContent(formulaElement.getKey().toString(), detailFormulaConverterDto.yearMonth));
        }
        return formulaElements;
    }
}
