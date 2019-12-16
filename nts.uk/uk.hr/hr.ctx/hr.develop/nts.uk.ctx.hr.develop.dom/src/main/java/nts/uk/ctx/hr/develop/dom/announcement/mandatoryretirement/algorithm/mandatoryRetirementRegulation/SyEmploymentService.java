package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentInfoImport;

public interface SyEmploymentService {

	List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,Optional<Boolean> getEmpExternalCode,
			Optional<Boolean> getMemo,Optional<Boolean> getempCommonMasterID,Optional<Boolean> getempCommonMasterItemID);
}
