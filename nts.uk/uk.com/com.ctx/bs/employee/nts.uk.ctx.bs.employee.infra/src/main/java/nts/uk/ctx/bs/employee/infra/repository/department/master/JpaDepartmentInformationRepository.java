package nts.uk.ctx.bs.employee.infra.repository.department.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformationRepository;
import nts.uk.ctx.bs.employee.infra.entity.department_new.BsymtDepartmentInfor;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaDepartmentInformationRepository extends JpaRepository implements DepartmentInformationRepository {

	@Override
	public List<DepartmentInformation> getAllDepartmentByCompany(String companyId, String depHistId) {
		String query = "SELECT i FROM BsymtDepartmentInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.departmentHistoryId = :depHistId";
		return this.queryProxy().query(query, BsymtDepartmentInfor.class).setParameter("companyId", companyId)
				.setParameter("depHistId", depHistId).getList(i -> i.toDomain());
	}

	@Override
	public List<DepartmentInformation> getAllActiveDepartmentByCompany(String companyId, String depHistId) {
		String query = "SELECT i FROM BsymtDepartmentInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.departmentHistoryId = :depHistId AND i.deleteFlag = 0";
		return this.queryProxy().query(query, BsymtDepartmentInfor.class).setParameter("companyId", companyId)
				.setParameter("depHistId", depHistId).getList(i -> i.toDomain());
	}

	@Override
	public void addDepartment(DepartmentInformation department) {
		this.commandProxy().insert(BsymtDepartmentInfor.fromDomain(department));
	}

	@Override
	public void addDepartments(List<DepartmentInformation> listDepartment) {
		List<BsymtDepartmentInfor> listEntity = listDepartment.stream().map(d -> BsymtDepartmentInfor.fromDomain(d))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(listEntity);
	}

	@Override
	public void deleteDepartmentInfo(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
