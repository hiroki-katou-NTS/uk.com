package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員雇用保険事業所履歴
*/
public interface EmpEstabInsHistRepository {

    Optional<EmpEstabInsHist> getEmpInsHistById(String cid, String sid, String histId);
    Optional<EmpEstabInsHist> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate);
    List<EmpInsOffice> getByHistIdsAndDate(List<String> histIds, GeneralDate endDate);
}
