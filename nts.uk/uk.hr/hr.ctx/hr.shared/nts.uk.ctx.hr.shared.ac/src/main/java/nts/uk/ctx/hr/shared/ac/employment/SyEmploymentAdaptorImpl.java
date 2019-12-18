package nts.uk.ctx.hr.shared.ac.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employment.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employment.EmploymentInfoExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.hr.shared.dom.employment.EmployeeBasicInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.EmploymentInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.ObjectParam;
import nts.uk.ctx.hr.shared.dom.employment.SyEmploymentAdaptor;

@Stateless
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
				.stream().map(export -> toEmploymentInfoImport(export)).collect(Collectors.toList());

	}

	private EmploymentInfoImport toEmploymentInfoImport(EmploymentInfoExport export) {
		return EmploymentInfoImport.builder().companyId(export.getCompanyId())
				.employmentCode(export.getEmploymentCode()).employmentName(export.getEmploymentName())
				.empExternalCode(export.getEmpExternalCode()).memo(export.getMemo())
				.empCommonMasterId(export.getEmpCommonMasterId()).empCommonMasterItemId(export.getEmpCommonMasterItemId())
				.build();
	}

	@Override
	public List<EmployeeBasicInfoImport> getEmploymentBasicInfo(List<ObjectParam> listObjParam, GeneralDate baseDate,
			String cid) {
		return this.sysEmp
				.getEmploymentBasicInfo(listObjParam.stream().map(x -> toObjParam(x)).collect(Collectors.toList()),
						baseDate, cid)
				.stream().map(x -> toEmployeeBasicInfoImport(x)).collect(Collectors.toList());
	}

	private nts.uk.ctx.bs.employee.pub.employment.ObjectParam toObjParam(ObjectParam object) {
		return new nts.uk.ctx.bs.employee.pub.employment.ObjectParam(object.getEmploymentCode(),
				object.getBirthdayPeriod());
	}

	private EmployeeBasicInfoImport toEmployeeBasicInfoImport(EmployeeBasicInfoExport export) {
		return EmployeeBasicInfoImport.builder().employmentCode(export.getEmploymentCode())
				.dateJoinComp(export.getDateJoinComp()).sid(export.getSid()).birthday(export.getBirthday())
				.pid(export.getPid()).build();
	}

}
