package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import java.util.Optional;
import java.util.List;

/**
* 社員家族社会保険履歴
*/
public interface EmpFamilyInsHisRepository {

    Optional<EmpFamilyInsHis> getListEmFamilyHis(String empId, int familyId);


}
