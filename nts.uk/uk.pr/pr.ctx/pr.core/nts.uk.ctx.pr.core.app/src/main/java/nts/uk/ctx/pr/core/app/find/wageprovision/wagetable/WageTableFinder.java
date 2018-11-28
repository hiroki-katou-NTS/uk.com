package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class WageTableFinder {

    @Inject
    private WageTableService wageTableService;

    @Inject
    private WageTableRepository wageTableRepo;

    public List<WageTableDto> getWageTableByYearMonth(int yearMonth) {
        return wageTableService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(WageTableDto::fromDomain).collect(Collectors.toList());
    }

    public WageTableDto getWageTableById(String wageTableCode) {
        String cid = AppContexts.user().companyId();
        Optional<WageTable> domainOtp = wageTableRepo.getWageTableById(cid, wageTableCode);
        return domainOtp.map(WageTableDto::fromDomain).orElse(null);
    }
}
