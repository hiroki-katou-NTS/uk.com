package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSettingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* かんたん計算式設定: Finder
*/
public class BasicFormulaSettingFinder
{

    @Inject
    private BasicFormulaSettingRepository basicFormulaSettingRepository;

    public BasicFormulaSettingDto getBasicFormulaSettingByHistoryID(String historyID){
        return basicFormulaSettingRepository.getBasicFormulaSettingById(historyID).map(BasicFormulaSettingDto::fromDomainToDto).orElse(null);
    }

}
