/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.repository.laborcontracthistory;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.EmployeeInfoDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistory;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistoryDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm.LaborContractHistoryRepository;
import nts.uk.ctx.hr.shared.infra.entity.laborcontracthistory.PpedtJmWorkHist;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaLaborContractHistoryRepository extends JpaRepository implements LaborContractHistoryRepository {

	@Override
	public List<LaborContractHistory> getListDomain(List<String> sids, GeneralDate baseDate) {

		String query = "SELECT c FROM PpedtJmWorkHist c "
				+ " WHERE c.startDate <= :baseDate and c.endDate >= :baseDate" + " AND c.sId IN :sids";
		
		List<PpedtJmWorkHist> entitys = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			entitys.addAll(this.queryProxy().query(query, PpedtJmWorkHist.class)
					.setParameter("sids", sids)
					.setParameter("baseDate", baseDate).getList());
		});
		
		if (entitys.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<LaborContractHistory> result = entitys.stream().map(e -> e.toDomain()).collect(Collectors.toList());
		return result;

	}

	@Override
	public List<WageTypeDto> getListWageType(List<String> sids, GeneralDate baseDate) {
		
		String query = "SELECT a.sId, b.wageType FROM PpedtJmWorkHist a "
				+ "INNER JOIN PpedtJmWork b ON a.histId = b.hisId "
				+ " WHERE a.startDate <= :baseDate and a.endDate >= :baseDate AND a.sId IN :sids ";
		
		List<Object[]> objects = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			objects.addAll(this.queryProxy().query(query, Object[].class)
					.setParameter("sids", sids)
					.setParameter("baseDate", baseDate).getList());
		});
		
		if (objects.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<WageTypeDto> result = objects.stream().map(object -> {
			return new WageTypeDto(
					object[0] == null ? null : object[0].toString(), 
					object[1] == null ? null : Integer.valueOf(object[1].toString()));
		}).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public List<EmployeeInfoDto> getListEmployeeInfoDto(String cid, GeneralDate startDate, GeneralDate endDate) {
		
		String query = "SELECT a FROM PpedtJmWorkHist a "
				+ " WHERE a.endDate >= :startDate and a.endDate <= :endDate AND a.cid = :cid ";
		
		List<PpedtJmWorkHist> entitys = this.queryProxy().query(query, PpedtJmWorkHist.class)
				.setParameter("cid", cid)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList();
		if (entitys.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<EmployeeInfoDto> result = entitys.stream().map(e -> {
			EmployeeInfoDto emp = EmployeeInfoDto.builder()
					.sid(e.sId)
					.startDate(e.startDate)
					.endDate(e.endDate).build();
			return emp;
		}).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public List<LaborContractHistoryDto> getListDomainByListSidAndStartDate(String cid, List<String> sids, GeneralDate startDate) {
		
		 String queryString = "SELECT a.cId, a.sId, a.histId, a.startDate, a.endDate FROM PpedtJmWorkHist a "
				+ " WHERE a.startDate >= :startDate AND a.cid = :cid AND a.sId IN :sids";
		 
		List<Object[]> objects = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			objects.addAll(this.queryProxy().query(queryString, Object[].class)
					.setParameter("sids", sids)
					.setParameter("cid", cid)
					.setParameter("startDate", startDate).getList());
		});
		
		if (objects.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<LaborContractHistoryDto> result = objects.stream().map(object -> {
			return new LaborContractHistoryDto(
					object[0] == null ? null : object[0].toString(), 
					object[1] == null ? null : object[1].toString(), 
					object[2] == null ? null : object[2].toString(), 
					object[3] == null ? null : GeneralDate.fromString(object[3].toString(), "yyyy/MM/dd"), 
					object[4] == null ? null : GeneralDate.fromString(object[4].toString(), "yyyy/MM/dd"));
		}).collect(Collectors.toList());
		
		return result;
		
	}

}
