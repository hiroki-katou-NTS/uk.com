package nts.uk.ctx.bs.employee.infra.repository.department_new;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentInformationRepository;
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
	public void deleteDepartmentInfo(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
