package nts.uk.ctx.hr.develop.ac;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.SyEmploymentService;

public class SyEmploymentServiceImpl implements SyEmploymentService{

	@Inject
	private SyEmploymentPub sysEmploymentPub;
	
	@Override
	public List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,
			Optional<Boolean> getEmpExternalCode, Optional<Boolean> getMemo, Optional<Boolean> getempCommonMasterID,
			Optional<Boolean> getempCommonMasterItemID) {
		return sysEmploymentPub.getEmploymentInfo(cid, getEmploymentName, getEmpExternalCode, getMemo, getempCommonMasterID, getempCommonMasterItemID)
				.stream().map(c -> 
					new EmploymentInfoImport(c.getCompanyId(), 
							c.getEmploymentCode(), 
							c.getEmploymentName(), 
							c.getEmpExternalCode(), 
							c.getMemo(), 
							c.getEmpCommonMasterId(), 
							c.getEmpCommonMasterItemId())).collect(Collectors.toList());
	}

}
