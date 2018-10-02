package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyHist;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyHistPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AffCompanyHistRepositoryImp extends JpaRepository implements AffCompanyHistRepository {

	private static final String DELETE_NO_PARAM = String.join(" ", "DELETE FROM BsymtAffCompanyHist c");

	private static final String DELETE_BY_PERSON_ID = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId");

	private static final String DELETE_BY_PID_EMPID = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId", "AND c.bsymtAffCompanyHistPk.sId = :sId");

	private static final String DELETE_BY_PRIMARY_KEY = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId", "AND c.bsymtAffCompanyHistPk.sId = :sId",
			"AND c.bsymtAffCompanyHistPk.historyId = :histId");

	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT c FROM BsymtAffCompanyHist c");

	private static final String SELECT_BY_PERSON_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId");

	private static final String SELECT_BY_EMPLOYEE_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.sId = :sId ORDER BY c.startDate ");

	private static final String SELECT_BY_EMPLOYEE_ID_DESC = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.sId = :sId and c.companyId = :cid ORDER BY c.startDate DESC");

	private static final String SELECT_BY_EMPLOYEE_ID_LIST = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.sId IN :sIdList  ORDER BY c.startDate ");

	private static final String SELECT_BY_EMPID_AND_BASE_DATE = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.sId = :sId", "AND c.startDate <= :baseDate", "AND c.endDate >= :baseDate",
			"ORDER BY c.startDate ");

	private static final String SELECT_BY_PRIMARY_KEY = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId", "AND c.bsymtAffCompanyHistPk.sId = :sId",
			"AND c.bsymtAffCompanyHistPk.historyId = :histId");

	private static final String SELECT_BY_HISTORY_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.historyId = :histId");

	private static final String SELECT_BY_EMPID_AND_DATE_PERIOD = String.join(" ", SELECT_NO_PARAM,
			" WHERE c.bsymtAffCompanyHistPk.sId IN :employeeIds   AND c.startDate <= :endDate AND :startDate <= c.endDate ");
	
	private static final String GET_LST_SID_BY_LSTSID_DATEPERIOD = "SELECT DISTINCT af.bsymtAffCompanyHistPk.sId FROM BsymtAffCompanyHist af " 
			+ " WHERE af.bsymtAffCompanyHistPk.sId IN :employeeIds AND af.startDate <= :endDate AND :startDate <= af.endDate";

	@Override
	public void add(AffCompanyHist domain) {
		this.commandProxy().insertAll(toEntities(domain));
		this.getEntityManager().flush();
	}

	@Override
	public void update(AffCompanyHist domain) {
		List<BsymtAffCompanyHist> entities = toEntities(domain);
		for (BsymtAffCompanyHist entity : entities) {
			BsymtAffCompanyHist update = queryProxy().query(SELECT_BY_PRIMARY_KEY, BsymtAffCompanyHist.class)
					.setParameter("pId", entity.bsymtAffCompanyHistPk.pId)
					.setParameter("sId", entity.bsymtAffCompanyHistPk.sId)
					.setParameter("histId", entity.bsymtAffCompanyHistPk.historyId).getSingleOrNull();

			if (update != null) {
				update.destinationData = entity.destinationData;
				update.endDate = entity.endDate;
				update.startDate = entity.startDate;

				commandProxy().update(update);
			}
		}
	}

	@Override
	public void remove(AffCompanyHist domain) {
		remove(domain.getPId());
	}

	@Override
	public void remove(String pId, String employeeId, String hisId) {
		this.getEntityManager().createQuery(DELETE_BY_PRIMARY_KEY, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.setParameter("sId", employeeId).setParameter("histId", hisId).executeUpdate();
	}

	@Override
	public void remove(String pId, String employeeId) {
		this.getEntityManager().createQuery(DELETE_BY_PID_EMPID, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.setParameter("sId", employeeId).executeUpdate();
	}

	@Override
	public void remove(String pId) {
		this.getEntityManager().createQuery(DELETE_BY_PERSON_ID, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.executeUpdate();
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfPerson(String personId) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_PERSON_ID, BsymtAffCompanyHist.class).setParameter("pId", personId).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfEmployee(String employeeId) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_ID, BsymtAffCompanyHist.class).setParameter("sId", employeeId).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfEmployeeDesc(String cid, String employeeId) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_ID_DESC, BsymtAffCompanyHist.class).setParameter("sId", employeeId)
				.setParameter("cid", cid).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfEmployeeAndBaseDate(String employeeId, GeneralDate baseDate) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_EMPID_AND_BASE_DATE, BsymtAffCompanyHist.class).setParameter("sId", employeeId)
				.setParameter("baseDate", baseDate).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}
	
	private AffCompanyHist toDomain(List<BsymtAffCompanyHist> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		AffCompanyHist domain = new AffCompanyHist();

		for (BsymtAffCompanyHist item : entities) {
			if (domain.getPId() == null) {
				domain.setPId(item.bsymtAffCompanyHistPk.pId);
			}

			AffCompanyHistByEmployee affCompanyHistByEmployee = domain
					.getAffCompanyHistByEmployee(item.bsymtAffCompanyHistPk.sId);

			if (affCompanyHistByEmployee == null) {
				affCompanyHistByEmployee = new AffCompanyHistByEmployee();
				affCompanyHistByEmployee.setSId(item.bsymtAffCompanyHistPk.sId);

				domain.addAffCompanyHistByEmployee(affCompanyHistByEmployee);
			}

			AffCompanyHistItem affCompanyHistItem = affCompanyHistByEmployee
					.getAffCompanyHistItem(item.bsymtAffCompanyHistPk.historyId);

			if (affCompanyHistItem == null) {
				affCompanyHistItem = new AffCompanyHistItem();
				affCompanyHistItem.setDestinationData(item.destinationData == 1);
				affCompanyHistItem.setHistoryId(item.bsymtAffCompanyHistPk.historyId);
				affCompanyHistItem.setDatePeriod(new DatePeriod(item.startDate, item.endDate));

				affCompanyHistByEmployee.addAffCompanyHistItem(affCompanyHistItem);
			}
		}

		return domain;
	}

	private List<BsymtAffCompanyHist> toEntities(AffCompanyHist domain) {
		String companyId = AppContexts.user().companyId();
		List<BsymtAffCompanyHist> entities = new ArrayList<BsymtAffCompanyHist>();
		for (AffCompanyHistByEmployee hist : domain.getLstAffCompanyHistByEmployee()) {
			for (AffCompanyHistItem item : hist.getLstAffCompanyHistoryItem()) {
				BsymtAffCompanyHistPk entityPk = new BsymtAffCompanyHistPk(domain.getPId(), hist.getSId(),
						item.getHistoryId());
				BsymtAffCompanyHist entity = new BsymtAffCompanyHist(entityPk, companyId,
						BooleanUtils.toInteger(item.isDestinationData()), item.getDatePeriod().start(),
						item.getDatePeriod().end(), null);

				entities.add(entity);
			}
		}

		return entities;
	}

	/**
	 * Update entity from domain
	 * 
	 * @param item
	 * @param entity
	 */
	private void updateEntity(AffCompanyHistItem item, BsymtAffCompanyHist entity) {
		entity.startDate = item.start();
		entity.endDate = item.end();
	}

	/**
	 * Convert to entity
	 * 
	 * @param histItem
	 * @param pId
	 * @param sid
	 * @return BsymtAffCompanyHist
	 */
	private BsymtAffCompanyHist toEntity(AffCompanyHistItem histItem, String pId, String sid) {
		String companyId = AppContexts.user().companyId();
		BsymtAffCompanyHistPk bsymtAffCompanyHistPk = new BsymtAffCompanyHistPk(pId, sid, histItem.getHistoryId());
		return new BsymtAffCompanyHist(bsymtAffCompanyHistPk, companyId, BooleanUtils.toInteger(histItem.isDestinationData()), histItem.start(), histItem.end(), null);
	}

	@Override
	public void add(String sid, String pId, AffCompanyHistItem item) {
		this.commandProxy().insert(toEntity(item, pId, sid));
	}

	@Override
	public void update(AffCompanyHistItem itemToBeUpdated) {

		Optional<BsymtAffCompanyHist> existItem = this.queryProxy()
				.query(SELECT_BY_HISTORY_ID, BsymtAffCompanyHist.class)
				.setParameter("histId", itemToBeUpdated.getHistoryId()).getSingle();

		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid AffCompanyHistItem");
		}
		updateEntity(itemToBeUpdated, existItem.get());
		this.commandProxy().update(existItem.get());
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfHistInfo(String histId) {
		List<BsymtAffCompanyHist> existItem = this.queryProxy().query(SELECT_BY_HISTORY_ID, BsymtAffCompanyHist.class)
				.setParameter("histId", histId).getList();

		return toDomain(existItem);
	}

	@Override
	public List<AffCompanyHist> getAffCompanyHistoryOfEmployees(List<String> employeeIds) {
		// OutPut Data
		List<AffCompanyHist> resultData = new ArrayList<>();
		// CHECK EMPTY of employeeIds
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		// ResultList
		List<BsymtAffCompanyHist> resultList = new ArrayList<>();
		// Split employeeId List if size of employeeId List is greater than 1000
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
			.query(SELECT_BY_EMPLOYEE_ID_LIST, BsymtAffCompanyHist.class).setParameter("sIdList", subList).getList();
			resultList.addAll(lstBsymtAffCompanyHist);
		});

		// check empty ResultList
		if (CollectionUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		// Convert Result List to Map
		Map<String, List<BsymtAffCompanyHist>> resultMap = resultList.parallelStream()
				.collect(Collectors.groupingBy(item -> item.bsymtAffCompanyHistPk.pId));

		// Foreach Map: Convert to Domain then add to Output List
		resultMap.entrySet().forEach(data -> {
			AffCompanyHist affComHist = this.toDomain(data.getValue());
			resultData.add(affComHist);
		});

		return resultData;
	}
	
	@Override
	public List<AffCompanyHistByEmployee> getAffEmployeeHistory(List<String> employeeIds) {

		if (employeeIds.isEmpty()) {
			return new ArrayList<>();
		}

		// ResultList
		List<BsymtAffCompanyHist> entities = new ArrayList<>();
		// Split employeeId List if size of employeeId List is greater than 1000
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
					.query(SELECT_BY_EMPLOYEE_ID_LIST, BsymtAffCompanyHist.class).setParameter("sIdList", subList)
					.getList();
			entities.addAll(lstBsymtAffCompanyHist);
		});

		// Convert Result List to Map
		Map<String, List<BsymtAffCompanyHist>> resultMap = entities.stream()
				.collect(Collectors.groupingBy(item -> item.bsymtAffCompanyHistPk.sId));

		List<AffCompanyHistByEmployee> resultList = new ArrayList<>();
		
		resultMap.forEach((employeeId, entitiesOfEmp) -> {
			List<AffCompanyHistItem> lstAffCompanyHistoryItem = entitiesOfEmp
					.stream().map(ent -> new AffCompanyHistItem(ent.bsymtAffCompanyHistPk.historyId,
							ent.destinationData == 1, new DatePeriod(ent.startDate, ent.endDate)))
					.collect(Collectors.toList());
			AffCompanyHistByEmployee empHist = new AffCompanyHistByEmployee(employeeId, lstAffCompanyHistoryItem);
			resultList.add(empHist);
		});
		
		return resultList;
	}

	@Override
	public List<AffCompanyHist> getAffComHisEmpByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod) {
		// OutPut Data
		List<AffCompanyHist> resultData = new ArrayList<>();
		// CHECK EMPTY of employeeIds
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		// ResultList
		List<BsymtAffCompanyHist> resultList = new ArrayList<>();
		// Split employeeId List if size of employeeId List is greater than 1000
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
					.query(SELECT_BY_EMPID_AND_DATE_PERIOD, BsymtAffCompanyHist.class)
					.setParameter("employeeIds", subList)
					.setParameter("startDate", datePeriod.start())
					.setParameter("endDate", datePeriod.end())
					.getList();
			resultList.addAll(lstBsymtAffCompanyHist);
		});

		// check empty ResultList
		if (CollectionUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		// Convert Result List to Map
		Map<String, List<BsymtAffCompanyHist>> resultMap = resultList.stream()
				.collect(Collectors.groupingBy(item -> item.bsymtAffCompanyHistPk.pId));

		// Foreach Map: Convert to Domain then add to Output List
		resultMap.entrySet().forEach(data -> {
			AffCompanyHist affComHist = this.toDomain(data.getValue());
			resultData.add(affComHist);
		});

		return resultData;
	}

	@Override
	public List<String> getLstSidByLstSidAndPeriod(List<String> employeeIds, DatePeriod dateperiod) {
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
