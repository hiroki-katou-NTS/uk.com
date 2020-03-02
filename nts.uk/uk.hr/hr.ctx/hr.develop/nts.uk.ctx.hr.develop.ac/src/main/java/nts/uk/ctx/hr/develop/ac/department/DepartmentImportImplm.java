package nts.uk.ctx.hr.develop.ac.department;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.department.aff.AffDepartmentPub;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentPub;
import nts.uk.ctx.bs.employee.pub.employee.EmpInfo614Param;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.IDepartmentInforImport;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.DepartmentInforImport;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.RequestList643Import;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.ResultRequest600Import;

@Stateless
public class DepartmentImportImplm implements IDepartmentInforImport {

	@Inject
	private DepartmentPub departmentPub;
	
	@Inject
	private AffDepartmentPub affDepartmentPub;
	
	@Inject
	private SyEmployeePub syEmployeePub;

	@Override
	public List<DepartmentInforImport> getAllActiveDepartment(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return departmentPub.getAllActiveDepartment(companyId, baseDate).stream().map(c -> new DepartmentInforImport(
				c.getDepartmentId(), 
				c.getHierarchyCode(), 
				c.getDepartmentCode(), 
				c.getDepartmentName(), 
				c.getDepartmentDisplayName(), 
				c.getDepartmentGenericName(), 
				c.getDepartmentExternalCode())
				).collect(Collectors.toList());
	}

	@Override
	public List<RequestList643Import> getAffDeptHistByEmpIdAndBaseDate(List<String> sids, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return affDepartmentPub.getAffDeptHistByEmpIdAndBaseDate(sids, baseDate).stream().map(c->new RequestList643Import(c.getEmployeeId(), c.getDepartmentId())).collect(Collectors.toList());
	}

	@Override
	public List<ResultRequest600Import> getEmpInfoLstBySids(List<String> sids, DatePeriod period, boolean isDelete,
			boolean isGetAffCompany) {
		// TODO Auto-generated method stub
		return syEmployeePub.getEmpInfoLstBySids(sids, period, isDelete, isGetAffCompany).stream().map(c-> new ResultRequest600Import(c.getSid(), c.getEmployeeCode(), c.getEmployeeName())).collect(Collectors.toList());
	}

	@Override
	public List<String> getDepartmentIdAndChildren(String companyId, GeneralDate baseDate, String departmentId) {
		// TODO Auto-generated method stub
		return departmentPub.getDepartmentIdAndChildren(companyId, baseDate, departmentId).stream().map(c->c).collect(Collectors.toList());
	}

	@Override
	public List<RequestList643Import> getAffDepartmentHistoryItems(List<String> departmentIDs, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return affDepartmentPub.getAffDepartmentHistoryItems(departmentIDs, baseDate).stream().map(c->new RequestList643Import(c.getEmployeeId(), c.getDepartmentId())).collect(Collectors.toList());
	}

	@Override
	public List<String> searchEmployeeByKey(String companyId, String key) {
		EmpInfo614Param param = new EmpInfo614Param(
				GeneralDate.today().toString("yyyy/MM/dd"), 
				companyId, 
				key, 
				false, 
				false, 
				false, 
				false, 
				false, 
				true);
		return syEmployeePub.findEmpByKeyWordsListSid(param).stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
	}
	
	

}
