package nts.uk.ctx.hr.develop.ac.department;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.department.aff.AffDepartmentPub;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentInforExport;
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
		List<DepartmentInforExport> lstHWkpInfo = departmentPub.getAllActiveDepartment(companyId, baseDate);
		return this.createTree(lstHWkpInfo);
	}
	
	private List<DepartmentInforImport> createTree(List<DepartmentInforExport> lstHWkpInfo) {
		List<DepartmentInforImport> lstReturn = new ArrayList<>();
		if (lstHWkpInfo.isEmpty())
			return lstReturn;
		// Higher hierarchyCode has shorter length
		int highestHierarchy = lstHWkpInfo.stream()
				.min((a, b) -> a.getHierarchyCode().length() - b.getHierarchyCode().length()).get().getHierarchyCode()
				.length();
		Iterator<DepartmentInforExport> iteratorWkpHierarchy = lstHWkpInfo.iterator();
		// while have workplace
		while (iteratorWkpHierarchy.hasNext()) {
			// pop 1 item
			DepartmentInforExport wkpHierarchy = iteratorWkpHierarchy.next();
			// convert
			DepartmentInforImport dto = new DepartmentInforImport(wkpHierarchy.getDepartmentId(), wkpHierarchy.getHierarchyCode(), wkpHierarchy.getDepartmentCode(), wkpHierarchy.getDepartmentName(), wkpHierarchy.getDepartmentDisplayName(), wkpHierarchy.getDepartmentGenericName(), wkpHierarchy.getDepartmentExternalCode(), new ArrayList<>());
			// build List
			this.pushToList(lstReturn, dto, wkpHierarchy.getHierarchyCode(), Strings.EMPTY, highestHierarchy);
		}
		return lstReturn;
	}
	
	private static final Integer HIERARCHY_LENGTH = 3;
	
	private void pushToList(List<DepartmentInforImport> lstReturn, DepartmentInforImport dto, String hierarchyCode, String preCode,
			int highestHierarchy) {
		if (hierarchyCode.length() == highestHierarchy) {
			// check duplicate code
			if (lstReturn.isEmpty()) {
				lstReturn.add(dto);
				return;
			}
			for (DepartmentInforImport item : lstReturn) {
				if (!item.getDepartmentId().equals(dto.getDepartmentId())) {
					lstReturn.add(dto);
					break;
				}
			}
		} else {
			String searchCode = preCode.isEmpty() ? preCode + hierarchyCode.substring(0, highestHierarchy)
					: preCode + hierarchyCode.substring(0, HIERARCHY_LENGTH);
			Optional<DepartmentInforImport> optWorkplaceFindDto = lstReturn.stream()
					.filter(item -> item.getHierarchyCode().equals(searchCode)).findFirst();
			if (!optWorkplaceFindDto.isPresent()) {
				return;
			}
			List<DepartmentInforImport> currentItemChilds = optWorkplaceFindDto.get().getChildren();
			pushToList(currentItemChilds, dto, hierarchyCode.substring(HIERARCHY_LENGTH, hierarchyCode.length()),
					searchCode, highestHierarchy);
		}
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
