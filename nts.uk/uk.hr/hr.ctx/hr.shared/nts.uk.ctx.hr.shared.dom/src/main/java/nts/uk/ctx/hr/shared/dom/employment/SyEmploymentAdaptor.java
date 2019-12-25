package nts.uk.ctx.hr.shared.dom.employment;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SyEmploymentAdaptor {

	public List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,
			Optional<Boolean> getEmpExternalCode, Optional<Boolean> getMemo, Optional<Boolean> getempCommonMasterID,
			Optional<Boolean> getempCommonMasterItemID);
	
	
	public List<EmployeeBasicInfoImport> getEmploymentBasicInfo(List<ObjectParam> listObjParam, GeneralDate baseDate, String cid);
}
