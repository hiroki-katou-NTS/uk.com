package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;

import java.util.List;
import java.util.Optional;

public interface NotifiOfChangInNameInsPerExRepository {
    List<LaborInsuranceOffice> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate);
}
