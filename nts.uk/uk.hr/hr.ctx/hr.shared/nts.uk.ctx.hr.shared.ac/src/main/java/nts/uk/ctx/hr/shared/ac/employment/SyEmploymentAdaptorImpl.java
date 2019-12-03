package nts.uk.ctx.hr.shared.ac.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employment.EmploymentInfoExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.hr.shared.dom.employment.EmploymentInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.SyEmploymentAdaptor;

public class SyEmploymentAdaptorImpl implements SyEmploymentAdaptor {

	@Inject
	private SyEmploymentPub sysEmp;

	@Override
	public List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,
			Optional<Boolean> getEmpExternalCode, Optional<Boolean> getMemo, Optional<Boolean> getempCommonMasterID,
			Optional<Boolean> getempCommonMasterItemID) {
		
		return this.sysEmp
				.getEmploymentInfo(cid, getEmploymentName, getEmpExternalCode, getMemo, getempCommonMasterID,
						getempCommonMasterItemID)
				.stream().map(export -> fromExport(export)).collect(Collectors.toList());
		
	}

	private EmploymentInfoImport fromExport(EmploymentInfoExport export) {
		return EmploymentInfoImport
				.builder()
				.companyId(export.getCompanyId())
				.employmentCode(export.getEmploymentCode())
				.employmentName(export.getEmploymentName())
				.empExternalCode(export.getEmpExternalCode())
				.memo(export.getMemo())
				.empCommonMasterId(export.getEmpCommonMasterId())
				.empCommonMasterItemId(export.getEmpCommonMasterId())
				.build();
	}

}
