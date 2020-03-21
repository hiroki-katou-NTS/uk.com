/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.repository.laborcontracthistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistory;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm.LaborContractHistoryRepository;
import nts.uk.ctx.hr.shared.infra.entity.laborcontracthistory.PpedtJmWorkHist;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaLaborContractHistoryRepository extends JpaRepository implements LaborContractHistoryRepository {

	private static final String getListDomain = "SELECT c FROM PpedtJmWorkHist c "
			+ " WHERE c.startDate <= :baseDate and c.endDate >= :baseDate" + " AND c.sId IN :sids";
	
	private static final String getListWageType = "SELECT a.sId FROM PpedtJmWorkHist a "
			+ "INNER JOIN PpedtJmWork b ON a.histId = b.hisId "
			+ " WHERE a.startDate <= :baseDate and a.endDate >= :baseDate AND a.sId IN :sids ";


	@Override
	public List<LaborContractHistory> getListDomain(List<String> sids, GeneralDate baseDate) {

		List<PpedtJmWorkHist> entitys = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			entitys.addAll(this.getEntityManager().createQuery(getListDomain)
					.setParameter("sids", sids)
					.setParameter("baseDate", baseDate).getResultList());
		});
		
		if (entitys.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<LaborContractHistory> result = entitys.stream().map(e -> e.toDomain()).collect(Collectors.toList());
		
		return result;

	}

	@Override
	public List<WageTypeDto> getListWageType(List<String> sids, GeneralDate baseDate) {
		List<Object[]> objects = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			objects.addAll(this.getEntityManager().createQuery(getListWageType)
					.setParameter("sids", sids)
					.setParameter("baseDate", baseDate).getResultList());
		});
		
		if (objects.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<WageTypeDto> result = objects.stream().map(object -> {
			return new WageTypeDto(object[0].toString(), object[1] == null ? null : Integer.valueOf(object[1].toString()));
		}).collect(Collectors.toList());
		
		return result;
	}

}
