package nts.uk.ctx.pr.file.app.core.empinsreportsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;

import java.util.List;
import java.util.Optional;

public interface EmpInsReportSettingExRepository {
    List<LaborInsuranceOffice> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate);
}
