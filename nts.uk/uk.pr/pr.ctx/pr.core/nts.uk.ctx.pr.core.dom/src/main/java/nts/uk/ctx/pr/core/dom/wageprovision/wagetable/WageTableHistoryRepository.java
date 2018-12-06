package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.time.YearMonth;

import java.util.List;

public interface WageTableHistoryRepository {

    public List<WageTableHistory> getWageTableHistByYearMonth(String cid, YearMonth yearMonth);

}
