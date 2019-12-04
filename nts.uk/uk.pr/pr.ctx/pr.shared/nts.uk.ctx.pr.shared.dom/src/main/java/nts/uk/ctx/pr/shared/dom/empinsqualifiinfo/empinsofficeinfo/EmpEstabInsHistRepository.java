package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員雇用保険事業所履歴
*/
public interface EmpEstabInsHistRepository {

    List<EmpInsOffice> getByHistIdsAndStartDateInPeriod(List<String> histIds, GeneralDate startDate, GeneralDate endDate);
    List<EmpInsOffice> getByHistIdsAndEndDateInPeriod(List<String> hisIds, GeneralDate startDate, GeneralDate endDate);
}
