package nts.uk.ctx.bs.employee.infra.repository.workplace_new;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace_new.BsymtWorkplaceInfor;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWorkplaceInformationRepository extends JpaRepository implements WorkplaceInformationRepository {

	@Override
	public List<WorkplaceInformation> getAllWorkplaceByCompany(String companyId, String wkpHistId) {
		String query = "SELECT i FROM BsymtWorkplaceInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.workplaceHistoryId = :depHistId";
		return this.queryProxy().query(query, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
				.setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
	}

	@Override
	public List<WorkplaceInformation> getAllActiveWorkplaceByCompany(String companyId, String wkpHistId) {
		String query = "SELECT i FROM BsymtWorkplaceInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.workplaceHistoryId = :depHistId AND i.deleteFlag = 0";
		return this.queryProxy().query(query, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
				.setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
	}
	
	@Override
	public void deleteDepartmentInfo(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
