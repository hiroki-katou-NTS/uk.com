package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class WageTableFinder {

    @Inject
    private WageTableService wageTableService;

    public List<WageTableDto> getWageTableByYearMonth(int yearMonth) {
        return wageTableService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(x -> WageTableDto.fromDomain(x)).collect(Collectors.toList());
    }

}
