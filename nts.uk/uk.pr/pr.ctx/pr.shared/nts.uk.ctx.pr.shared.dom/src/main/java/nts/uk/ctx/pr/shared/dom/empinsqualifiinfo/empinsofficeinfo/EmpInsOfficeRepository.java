package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import java.util.List;
import java.util.Optional;

/**
* 社員雇用保険事業所情報
*/
public interface EmpInsOfficeRepository {
    
    Optional<EmpInsOffice> getEmpInsOfficeById(String cid, String sid, String histId);

}
