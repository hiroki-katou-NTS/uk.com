package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import java.util.Optional;

/**
* 社員雇用保険事業所履歴
*/
public interface EmpEstabInsHistRepository {

    Optional<EmpEstabInsHist> getEmpInsHistById(String cid, String sid, String histId);

}
