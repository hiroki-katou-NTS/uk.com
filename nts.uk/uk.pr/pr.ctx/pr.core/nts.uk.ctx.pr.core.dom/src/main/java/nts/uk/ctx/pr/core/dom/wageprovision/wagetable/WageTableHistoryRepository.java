package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.time.YearMonth;

import java.util.List;
import java.util.Optional;

public interface WageTableHistoryRepository {

    public List<WageTableHistory> getWageTableHistByYearMonth(String cid, YearMonth yearMonth);
    
    public List<WageTableHistory> getWageTableHistByCodes(String companyId, List<String> codes);
    
    public Optional<WageTableHistory> getWageTableHistByCode(String companyId, String code);
    
    public void addOrUpdate(WageTableHistory domain);
    
    public void remove(String companyId, String code);

}
