package nts.uk.ctx.hr.shared.dom.employment;

import java.util.List;
import java.util.Optional;

public interface SyEmploymentAdaptor {

	public List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,
			Optional<Boolean> getEmpExternalCode, Optional<Boolean> getMemo, Optional<Boolean> getempCommonMasterID,
			Optional<Boolean> getempCommonMasterItemID);
}
