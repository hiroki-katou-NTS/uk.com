package nts.uk.ctx.basic.app.find.organization.department;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentMemo;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;

@Stateless
public class DepartmentFinder {
	
	@Inject
	private DepartmentRepository departmentRepository;
	
	public boolean checkExist(String companyCode){
		return departmentRepository.checkExist(companyCode);
	}
	
	public List<Department> findHistories(String companyCode){
		return departmentRepository.findHistories(companyCode);
	}
	
	public List<Department> findAllByHistory(String companyCode, String historyId){
		return departmentRepository.findAllByHistory(companyCode, historyId);
	}
	
	public Optional<DepartmentMemo> findMemo(String companyCode, String historyId){
		return departmentRepository.findMemo(companyCode, historyId);
	}

}
