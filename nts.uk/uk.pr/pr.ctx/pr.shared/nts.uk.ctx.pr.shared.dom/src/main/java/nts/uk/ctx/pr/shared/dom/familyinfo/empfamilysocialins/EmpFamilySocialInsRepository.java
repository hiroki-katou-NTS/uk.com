package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import nts.arc.time.GeneralDate;

import java.util.Optional;
import java.util.List;

/**
* 社員家族社会保険情報
*/
public interface EmpFamilySocialInsRepository {
    Optional<EmpFamilySocialIns> getEmpFamilySocialInsById (String empId, String familyId, String historyId);

}
