package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import nts.arc.time.GeneralDate;

import java.util.Optional;
import java.util.List;

/**
* 社員家族社会保険情報
*/
public interface EmpFamilySocialInsCtgRepository {

    Optional<EmpFamilySocialInsCtg> getEmpFamilySocialInsCtg(String empId, int familyId,  String historyId);
    List<EmpFamilySocialInsCtg> getEmpFamilySocialInsCtg(List<String> empIds, int familyId,  GeneralDate date);

}
