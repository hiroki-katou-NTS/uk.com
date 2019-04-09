package nts.uk.ctx.bs.employee.infra.repository.workplace.master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.master.BsymtWorkplaceInfor;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWorkplaceInformationRepository extends JpaRepository implements WorkplaceInformationRepository {

	private static final String FIND_ALL_WKP_BY_COMPANY_AND_HIST = "SELECT i FROM BsymtWorkplaceInfor i"
			+ " WHERE i.pk.companyId = :companyId " + "AND i.pk.workplaceHistoryId = :wkpHistId";
	private static final String FIND_ALL_ACTIVE_WKP_BY_COMPANY_AND_HIST = "SELECT i FROM BsymtWorkplaceInfor i"
			+ " WHERE i.pk.companyId = :companyId " + "AND i.pk.workplaceHistoryId = :wkpHistId AND i.deleteFlag = 0";

	@Override
	public List<WorkplaceInformation> getAllWorkplaceByCompany(String companyId, String wkpHistId) {
		return this.queryProxy().query(FIND_ALL_WKP_BY_COMPANY_AND_HIST, BsymtWorkplaceInfor.class)
				.setParameter("companyId", companyId).setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
	}

	@Override
	public List<WorkplaceInformation> getAllActiveWorkplaceByCompany(String companyId, String wkpHistId) {
		return this.queryProxy().query(FIND_ALL_ACTIVE_WKP_BY_COMPANY_AND_HIST, BsymtWorkplaceInfor.class)
				.setParameter("companyId", companyId).setParameter("wkpHistId", wkpHistId).getList(i -> i.toDomain());
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
	public void deleteWorkplaceInforOfHistory(String companyId, String wkpHistId) {
		List<BsymtWorkplaceInfor> listEntity = this.queryProxy()
				.query(FIND_ALL_WKP_BY_COMPANY_AND_HIST, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
				.setParameter("wkpHistId", wkpHistId).getList();
		this.commandProxy().removeAll(listEntity);
	}

	@Override
	public List<WorkplaceInformation> getWorkplaceByWkpIds(String companyId, String wkpHistId,
			List<String> listWorkplaceId) {
		if (listWorkplaceId.isEmpty())
			return Collections.emptyList();
		String query = "SELECT i FROM BsymtWorkplaceInfor i WHERE i.pk.companyId = :companyId "
				+ "AND i.pk.workplaceHistoryId = :wkpHistId AND i.pk.workplaceId IN :listWkpIds";
		List<WorkplaceInformation> result = new ArrayList<>();
		CollectionUtil.split(listWorkplaceId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			result.addAll(this.queryProxy().query(query, BsymtWorkplaceInfor.class).setParameter("companyId", companyId)
					.setParameter("wkpHistId", wkpHistId).setParameter("listWkpIds", subIdList)
					.getList(f -> f.toDomain()));
		});
		return result;
	}

}
