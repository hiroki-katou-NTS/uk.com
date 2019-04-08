package nts.uk.ctx.bs.employee.infra.repository.workplace.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
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
				+ "AND i.pk.workplaceHistoryId = :wkpHistId";
		return this.queryProxy().query(query, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
				.setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
	}

	@Override
	public List<WorkplaceInformation> getAllActiveWorkplaceByCompany(String companyId, String wkpHistId) {
		String query = "SELECT i FROM BsymtWorkplaceInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.workplaceHistoryId = :wkpHistId AND i.deleteFlag = 0";
		return this.queryProxy().query(query, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
				.setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
	}

	@Override
	public void addWorkplace(WorkplaceInformation workplace) {
		this.commandProxy().insert(BsymtWorkplaceInfor.fromDomain(workplace));
	}

	@Override
	public void addWorkplaces(List<WorkplaceInformation> listWorkplace) {
		List<BsymtWorkplaceInfor> listEntity = listWorkplace.stream().map(w -> BsymtWorkplaceInfor.fromDomain(w))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(listEntity);
	}

	@Override
	public void deleteDepartmentInfo(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
