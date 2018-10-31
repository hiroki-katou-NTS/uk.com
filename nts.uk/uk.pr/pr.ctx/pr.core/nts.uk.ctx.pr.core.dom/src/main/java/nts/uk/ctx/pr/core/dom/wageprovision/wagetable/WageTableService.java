package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class WageTableService {

    @Inject
    private WageTableRepository wageTableRepo;

    @Inject
    private WageTableHistRepository wageTableHistRepo;

    public List<WageTable> getFormulaByYearMonth(YearMonth yearMonth) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「賃金テーブル履歴」を取得する
        List<String> wageTableCodes = wageTableHistRepo.getWageTableHistByYearMonth(cid, yearMonth)
                .stream().map(x -> x.getWageTableCode().v()).collect(Collectors.toList());
        // ドメインモデル「賃金テーブル」を取得する
        return wageTableRepo.getWageTableByCodes(cid, wageTableCodes);
    }

}
