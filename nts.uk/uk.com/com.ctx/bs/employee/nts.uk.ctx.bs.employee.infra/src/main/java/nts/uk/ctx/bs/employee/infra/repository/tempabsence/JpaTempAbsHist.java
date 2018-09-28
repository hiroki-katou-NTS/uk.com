package nts.uk.ctx.bs.employee.infra.repository.tempabsence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaTempAbsHist extends JpaRepository implements TempAbsHistRepository {

	private static final String QUERY_GET_TEMPORARYABSENCE_BYSID = "SELECT ta FROM BsymtTempAbsHistory ta"
			+ " WHERE ta.sid = :sid and ta.cid = :cid ORDER BY ta.startDate";
	
	private static final String QUERY_GET_TEMPORARYABSENCE_BYSID_DESC = QUERY_GET_TEMPORARYABSENCE_BYSID + " DESC";
	
	private static final String GET_BY_SID_DATE = "select h from BsymtTempAbsHistory h"
			+ " where h.sid = :sid and h.startDate <= :standardDate and h.endDate >= :standardDate";
	private static final String SELECT_BY_LIST_SID_DATEPERIOD =  "SELECT th FROM BsymtTempAbsHistory th"
			+ " WHERE th.sid IN :employeeIds AND th.startDate <= :endDate AND :startDate <= th.endDate"
			+ " ORDER BY th.sid, th.startDate";
	
	private static final String GET_LST_SID_BY_LSTSID_DATEPERIOD = "SELECT tah.sid FROM BsymtTempAbsHistory tah" 
			+ " WHERE tah.sid IN :employeeIds AND tah.startDate <= :endDate AND :startDate <= tah.endDate ";
	
	/**
	 * Convert from domain to entity
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtTempAbsHistory toEntity(String companyId, String employeeID, DateHistoryItem item) {
		return new BsymtTempAbsHistory(item.identifier(), companyId, employeeID, item.start(), item.end());
	}

	/**
	 * Update entity from domain
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(DateHistoryItem item, BsymtTempAbsHistory entity) {
		entity.startDate = item.start();
		entity.endDate = item.end();
	}

	@Override
	public void add(String cid, String sid, DateHistoryItem item) {
		this.commandProxy().insert(toEntity(cid, sid, item));
	}

	@Override
	public void update(DateHistoryItem item) {

		Optional<BsymtTempAbsHistory> histItem = this.queryProxy().find(item.identifier(), BsymtTempAbsHistory.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		updateEntity(item, histItem.get());
		this.commandProxy().update(histItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtTempAbsHistory> histItem = this.queryProxy().find(histId, BsymtTempAbsHistory.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtTempAbsHistory");
		}
		this.commandProxy().remove(BsymtTempAbsHistory.class, histId);
	}

	@Override
	public Optional<DateHistoryItem> getByHistId(String histId) {

		Optional<BsymtTempAbsHistory> existItem = this.queryProxy().find(histId, BsymtTempAbsHistory.class);
		if (existItem.isPresent()) {
			BsymtTempAbsHistory entity = existItem.get();
			return Optional.of(new DateHistoryItem(entity.histId, new DatePeriod(entity.startDate, entity.endDate)));
		}
		return Optional.empty();

	}

	@Override
	public Optional<DateHistoryItem> getItemByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate) {
		Optional<BsymtTempAbsHistory> optionData = this.queryProxy().query(GET_BY_SID_DATE, BsymtTempAbsHistory.class)
				.setParameter("sid", employeeId).setParameter("standardDate", standardDate).getSingle();
		if (optionData.isPresent()) {
			BsymtTempAbsHistory entity = optionData.get();
			return Optional.of(new DateHistoryItem(entity.histId, new DatePeriod(entity.startDate, entity.endDate)));
		}
		return Optional.empty();
	}

	/**
	 * Convert to domain TempAbsenceHistory
	 * 
	 * @param employeeId
	 * @param listHist
	 * @return
	 */
	private TempAbsenceHistory toDomainTemp(List<BsymtTempAbsHistory> listHist) {
		TempAbsenceHistory domain = new TempAbsenceHistory(listHist.get(0).cid, listHist.get(0).sid, new ArrayList<DateHistoryItem>());
		for (BsymtTempAbsHistory item : listHist) {
			DateHistoryItem dateItem = new DateHistoryItem(item.histId, new DatePeriod(item.startDate, item.endDate));
			domain.getDateHistoryItems().add(dateItem);
		}
		return domain;
	}

	@Override
	public Optional<TempAbsenceHistory> getByEmployeeId(String cid, String employeeId) {
		List<BsymtTempAbsHistory> listHist = this.queryProxy()
				.query(QUERY_GET_TEMPORARYABSENCE_BYSID, BsymtTempAbsHistory.class).setParameter("sid", employeeId)
				.setParameter("cid", cid).getList();
		if (listHist != null && !listHist.isEmpty()) {
			return Optional.of(toDomainTemp(listHist));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<TempAbsenceHistory> getByEmployeeIdDesc(String cid, String employeeId) {
		List<BsymtTempAbsHistory> listHist = this.queryProxy()
				.query(QUERY_GET_TEMPORARYABSENCE_BYSID_DESC, BsymtTempAbsHistory.class).setParameter("sid", employeeId)
				.setParameter("cid", cid).getList();
		if (listHist != null && !listHist.isEmpty()) {
			return Optional.of(toDomainTemp(listHist));
		}
		return Optional.empty();
	}

	@Override
	public List<TempAbsenceHistory> getByListSid(List<String> employeeIds, DatePeriod dateperiod) {
		
		// ResultList
		List<BsymtTempAbsHistory> tempAbsHistoryEntities = new ArrayList<>();
		// Split employeeId List if size of employeeId List is greater than 1000
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			List<BsymtTempAbsHistory> lstBsymtAffCompanyHist = this.queryProxy()
					.query(SELECT_BY_LIST_SID_DATEPERIOD, BsymtTempAbsHistory.class)
					.setParameter("employeeIds", subList).setParameter("startDate", dateperiod.start())
					.setParameter("endDate", dateperiod.end()).getList();
			tempAbsHistoryEntities.addAll(lstBsymtAffCompanyHist);
		});
		
		Map<String, List<BsymtTempAbsHistory>> tempAbsEntityForEmp = tempAbsHistoryEntities.stream()
				.collect(Collectors.groupingBy(x -> x.sid));
		List<TempAbsenceHistory> resultList = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		tempAbsEntityForEmp.forEach((empId, listTempAbsHist) -> {
			List<DateHistoryItem> dateHistoryItems = convertToDateHistoryItems(listTempAbsHist);
			resultList.add(new TempAbsenceHistory(companyId, empId, dateHistoryItems));
		});
		return resultList;
	}
	
	private List<DateHistoryItem> convertToDateHistoryItems(List<BsymtTempAbsHistory> entities) {
		return entities.stream().map(ent -> new DateHistoryItem(ent.histId, new DatePeriod(ent.startDate, ent.endDate)))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getLstSidByListSidAndDatePeriod(List<String> employeeIds, DatePeriod dateperiod) {
		List<String> listSid = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			listSid.addAll(this.queryProxy().query(GET_LST_SID_BY_LSTSID_DATEPERIOD, String.class)
					.setParameter("employeeIds", subList)
					.setParameter("startDate", dateperiod.start())
					.setParameter("endDate", dateperiod.end())
					.getList());
		});
		if(listSid.isEmpty()){
			return Collections.emptyList();
		}
		return listSid;
	}

}
