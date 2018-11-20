package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FormulaService {

    @Inject
    private FormulaRepository formulaRepo;

    @Inject
    private FormulaHistRepository formulaHistRepo;

    public List<Formula> getFormulaByYearMonth(YearMonth yearMonth) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「計算式履歴」を取得する
        List<String> formulaCodes = formulaHistRepo.getFormulaHistByYearMonth(cid, yearMonth)
                .stream().map(x -> x.getFormulaCode().v()).collect(Collectors.toList());
        // ドメインモデル「計算式」を取得する
        return formulaRepo.getFormulaByCodes(cid, formulaCodes);
    }

}
