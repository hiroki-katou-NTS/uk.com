package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSettingRepository;

import java.util.List;
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

    public DetailFormulaSettingDto getDetailFormulaSettingByHistoryID(String historyID){
        return detailFormulaSettingRepository.getDetailFormulaSettingById(historyID).map(DetailFormulaSettingDto::fromDomainToDto).orElse(null);
    }

}
