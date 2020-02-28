package nts.uk.ctx.hr.develop.ac;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.pub.workrule.closure.workday.IWorkDayPub;
import nts.uk.ctx.bs.employee.pub.employment.ObjectParam;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeBasicInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeInformationImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.SearchCondition;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.SyEmploymentService;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

@Stateless
public class SyEmploymentServiceImpl implements SyEmploymentService{

	@Inject
	private SyEmploymentPub sysEmploymentPub;
	
	@Inject
	private EmployeeInformationPub employeeInformationPub;
	
	@Inject
	private IWorkDayPub iWorkDayPub;
	
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

	@Override
	public List<EmployeeBasicInfoImport> getEmploymentBasicInfo(List<SearchCondition> searchCondition,
			GeneralDate baseDate, String cid) {
		List<ObjectParam> listObjParam = searchCondition.stream().map(c->new ObjectParam(c.getEmploymentCode(), c.getBirthdayPeriod())).collect(Collectors.toList());
		return sysEmploymentPub.getEmploymentBasicInfo(listObjParam, baseDate, cid).stream().map(c-> new EmployeeBasicInfoImport(c.getEmploymentCode(), c.getDateJoinComp(), c.getSid(), c.getBirthday(), c.getPid())).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeInformationImport> getEmployeeInfor(List<String> employeeIds, GeneralDate referenceDate,
			boolean toGetWorkplace, boolean toGetDepartment, boolean toGetPosition, boolean toGetEmployment,
			boolean toGetClassification, boolean toGetEmploymentCls) {
		EmployeeInformationQueryDto param = new EmployeeInformationQueryDto(employeeIds, referenceDate, toGetWorkplace, toGetDepartment, toGetPosition, toGetEmployment, toGetClassification, toGetEmploymentCls);
		return employeeInformationPub.find(param).stream().map(c->{
			return new EmployeeInformationImport(
					c.getEmployeeId(), 
					c.getEmployeeCode(), 
					c.getBusinessName(), 
					c.getBusinessNameKana(), 
					c.getDepartment() == null ? null : c.getDepartment().getDepartmentId(), 
					c.getDepartment() == null ? null : c.getDepartment().getDepartmentCode(), 
					c.getDepartment() == null ? null : c.getDepartment().getDepartmentName(), 
					c.getPosition() == null ? null: c.getPosition().getPositionId(), 
					c.getPosition() == null ? null: c.getPosition().getPositionCode(), 
					c.getPosition() == null ? null: c.getPosition().getPositionName(), 
					c.getEmployment()== null? null: c.getEmployment().getEmploymentCode(), 
					c.getEmployment()== null? null: c.getEmployment().getEmploymentName());
		}).collect(Collectors.toList());
	}

	@Override
	public List<EmploymentDateDto> getClosureDate(String companyId) {
		// TODO Auto-generated method stub
		return iWorkDayPub.getClosureDate(companyId).stream().map(c-> new EmploymentDateDto(c.getEmploymentCd(), c.getClosureDate())).collect(Collectors.toList());
	}

}
