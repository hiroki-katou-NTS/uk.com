/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemCustom;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithEnumList;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionWithDataPeriod;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondHist;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondHistItem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWorkInfo;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWorkInfoPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWorkTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWorkTsPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem_;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Class JpaWorkingConditionItemRepository.
 */
@Stateless
public class JpaWorkingConditionItemRepository extends JpaRepository
		implements WorkingConditionItemRepository {

	/** The Constant FIRST_ITEM_INDEX. */
	private final static int FIRST_ITEM_INDEX = 0;

	/** The Constant FIND_BY_SID_AND_PERIOD_ORDER_BY_STR_D. */
//	private final static String FIND_BY_SID_AND_PERIOD_ORDER_BY_STR_D =
//			"SELECT wi FROM KshmtWorkcondHistItem wi "
//			+ "WHERE wi.sid = :employeeId "
//			+ "AND wi.kshmtWorkingCond.strD <= :endDate "
//			+ "AND wi.kshmtWorkingCond.endD >= :startDate "
//			+ "ORDER BY wi.kshmtWorkingCond.strD";
//	
//	private final static String FIND_BY_SID_AND_PERIOD_ORDER_BY_STR_D_FOR_MULTI =
//			"SELECT wi FROM KshmtWorkcondHistItem wi "
//			+ "WHERE wi.sid IN :employeeId "
//			+ "AND wi.kshmtWorkingCond.strD <= :endDate "
//			+ "AND wi.kshmtWorkingCond.endD >= :startDate "
//			+ "ORDER BY wi.kshmtWorkingCond.strD";
	
	private final static String FIND_BY_SID = "SELECT wi FROM KshmtWorkcondHistItem wi "
												+ "WHERE wi.sid IN :employeeIds "
												+ "AND wi.timeApply != NULL ";
	private final static String FIND_BY_SID_AND_PERIOD_WITH_JOIN = new StringBuilder("SELECT wi, c, m, wc, dw FROM KshmtWorkcondHistItem wi ")
																						.append(" LEFT JOIN wi.kshmtWorkingCond c ")
																						.append(" LEFT JOIN wi.kshmtScheduleMethod m ")
																						.append(" LEFT JOIN wi.kshmtWorkcondWorkInfo wc ")
																						.append(" LEFT JOIN wi.listKshmtWorkcondWorkTs dw ")
																						.append(" WHERE wi.sid IN :employeeId ")
																						.append(" AND c.strD <= :endDate ")
																						.append(" AND c.endD >= :startDate ")
																						.append(" ORDER BY c.strD").toString();
	private final static String FIND_BY_SID_AND_PERIOD_WITH_JOIN_NEW = new StringBuilder("SELECT wi, c, m, wc, dw FROM KshmtWorkcondHistItem wi ")
			.append(" LEFT JOIN wi.kshmtWorkingCond c ")
			.append(" LEFT JOIN wi.kshmtScheduleMethod m ")
			.append(" LEFT JOIN wi.kshmtWorkcondWorkInfo wc ")
			.append(" LEFT JOIN wi.listKshmtWorkcondWorkTs dw ")
			.append(" WHERE wi.sid IN :employeeId ")
			.append(" AND c.strD <= :endDate ")
			.append(" AND c.endD >= :startDate ").toString();
	
	public List<WorkingConditionItem> getByListSidAndTimeApplyNotNull(List<String> employeeIds){		
		List<KshmtWorkcondHistItem> result = new ArrayList<>();
		result = this.queryProxy().query(FIND_BY_SID, KshmtWorkcondHistItem.class )
				.setParameter("employeeIds", employeeIds)
				.getList();
		
		return result.stream()
				.map(e -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(e)))
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the by list sid and monthly pattern not null.
	 *
	 * @param employeeIds the employee ids
	 * @return the by list sid and monthly pattern not null
	 */
	public List<WorkingConditionItem> getByListSidAndMonthlyPatternNotNull(List<String> employeeIds, List<String> monthlyPatternCodes){
		if (CollectionUtil.isEmpty(employeeIds) || CollectionUtil.isEmpty(monthlyPatternCodes)) {
			return Collections.emptyList();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

		// select root
		cq.select(root);

		List<KshmtWorkcondHistItem> result = new ArrayList<>();
				
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
				subEmployeeList -> {
					CollectionUtil.split(monthlyPatternCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
							subPatternList -> {
								// add where
								List<Predicate> lstpredicateWhere = new ArrayList<>();

								// condition
								lstpredicateWhere.add(
										root.get(KshmtWorkingCondItem_.sid).in(subEmployeeList));
								lstpredicateWhere.add(root.get(KshmtWorkingCondItem_.monthlyPattern)
										.in(subPatternList));
								lstpredicateWhere
										.add(criteriaBuilder.equal(
												root.get(KshmtWorkingCondItem_.kshmtWorkingCond)
														.get(KshmtWorkingCond_.endD),
												GeneralDate.max()));

								// set where to SQL
								cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

								// create query
								TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

								result.addAll(query.getResultList());
							});
				});
		
		// exclude select
		return result.stream()
				.map(e -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(e)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * getByHistoryId(java.lang.String)
	 */
	@Override
	public Optional<WorkingConditionItem> getByHistoryId(String historyId) {
		// Query
		Optional<KshmtWorkcondHistItem> optEntity = this.queryProxy().find(historyId,
				KshmtWorkcondHistItem.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(
				new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * findWorkingConditionItemByPersWorkCat(java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId,
			GeneralDate baseDate) {
//		// get entity manager
//		EntityManager em = this.getEntityManager();
//		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//
//		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
//				.createQuery(KshmtWorkcondHistItem.class);
//
//		// root data
//		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);
//
//		// select root
//		cq.select(root);
//
//		// add where
//		List<Predicate> lstpredicateWhere = new ArrayList<>();
//
//		// equal
//		lstpredicateWhere
//				.add(criteriaBuilder.equal(root.get(KshmtWorkingCondItem_.sid), employeeId));
//		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
//				root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.strD),
//				baseDate));
//		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
//				root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD),
//				baseDate));
//
//		// set where to SQL
//		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
//
//		// create query
//		TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);
//
//		List<KshmtWorkcondHistItem> result = query.getResultList();
//
//		// Check empty
//		if (CollectionUtil.isEmpty(result)) {
//			return Optional.empty();
//		}
//		
//		// exclude select
//		Optional<WorkingConditionItem> data =  Optional.of(new WorkingConditionItem(
//				new JpaWorkingConditionItemGetMemento(result.get(FIRST_ITEM_INDEX))));
    	List<WorkingConditionItem> data = getBySidAndPeriodOrderByStrD(employeeId, new DatePeriod(baseDate, baseDate));
    	if(data.isEmpty()) {
    		return Optional.empty();
    	}
		return Optional.of(data.get(0));
	}

	@Override
	public List<WorkingConditionItemCustom> getBySidsAndStandardDate(List<String> employeeIds, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

		// select root
		cq.select(root);

		List<KshmtWorkcondHistItem> result = new ArrayList<>();

		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// equal
			lstpredicateWhere.add(root.get(KshmtWorkingCondItem_.sid).in(subList));
			lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
					root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.strD),
					baseDate));
			lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
					root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD),
					baseDate));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[]{}));

			// creat query
			TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

			result.addAll(query.getResultList());
		});

		// Check empty
		if (CollectionUtil.isEmpty(result)) {
			return Collections.emptyList();
		}

		// exclude select
		return result.stream().map(entity -> new WorkingConditionItemCustom(entity.getSid(), entity.getLaborSys()))
				.collect(Collectors.toList());
	}

	/**
	 * Gets the list by sid and date period
	 *
	 * @param employeeId the employee id
	 * @param datePeriod the date period
	 * @return the list
	 */
	// add 2018.1.31 shuichi_ishida
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SneakyThrows
	@Override
	public List<WorkingConditionItem> getBySidAndPeriodOrderByStrD(String employeeId,
			DatePeriod datePeriod) {
		String sqlJdbc = "SELECT KWCI.*, KSM.* FROM KSHMT_WORKCOND_HIST_ITEM KWCI "
				+ "LEFT JOIN KSHMT_WORKCOND_SCHE_METH KSM ON KWCI.HIST_ID = KSM.HIST_ID "
				+ "LEFT JOIN KSHMT_WORKCOND_HIST KWC ON KWCI.HIST_ID = KWC.HIST_ID "
				+ "WHERE KWCI.SID = ? " + "AND KWC.START_DATE <= ? " + "AND KWC.END_DATE >= ? "
				+ "ORDER BY KWC.START_DATE";

		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {

			stmt.setString(1, employeeId);
			stmt.setDate(2, Date.valueOf(datePeriod.end().toLocalDate()));
			stmt.setDate(3, Date.valueOf(datePeriod.start().toLocalDate()));

			List<KshmtWorkcondHistItem> result = new NtsResultSet(stmt.executeQuery())
					.getList(rec -> {
						KshmtWorkcondScheMeth kshmtScheduleMethod = new KshmtWorkcondScheMeth();
						kshmtScheduleMethod.setHistoryId(rec.getString("HIST_ID"));
						kshmtScheduleMethod.setBasicCreateMethod(rec.getInt("BASIC_CREATE_METHOD"));
						kshmtScheduleMethod
								.setRefBusinessDayCalendar(rec.getInt("REF_BUSINESS_DAY_CALENDAR"));
						kshmtScheduleMethod.setRefWorkingHours(rec.getInt("REF_WORKING_HOURS"));

						KshmtWorkcondHistItem entity = new KshmtWorkcondHistItem();
						entity.setHistoryId(rec.getString("HIST_ID"));
						entity.setSid(rec.getString("SID"));
						entity.setHourlyPayAtr(rec.getInt("HOURLY_PAY_ATR"));
						entity.setScheManagementAtr(rec.getInt("SCHE_MANAGEMENT_ATR"));
						entity.setAutoStampSetAtr(rec.getInt("AUTO_STAMP_SET_ATR"));
						entity.setAutoIntervalSetAtr(rec.getInt("AUTO_INTERVAL_SET_ATR"));
						entity.setVacationAddTimeAtr(rec.getInt("VACATION_ADD_TIME_ATR"));
						entity.setContractTime(rec.getInt("CONTRACT_TIME"));
						entity.setLaborSys(rec.getInt("LABOR_SYS"));
						entity.setHdAddTimeOneDay(rec.getInt("HD_ADD_TIME_ONE_DAY"));
						entity.setHdAddTimeMorning(rec.getInt("HD_ADD_TIME_MORNING"));
						entity.setHdAddTimeAfternoon(rec.getInt("HD_ADD_TIME_AFTERNOON"));
						entity.setTimeApply(rec.getString("TIME_APPLY"));
						entity.setMonthlyPattern(rec.getString("MONTHLY_PATTERN"));
						entity.setKshmtScheduleMethod(kshmtScheduleMethod);

						return entity;
					});

			if (result.isEmpty()) {
				return Collections.emptyList();
			}

			List<String> histIds = result.stream().map(KshmtWorkcondHistItem::getHistoryId)
					.collect(Collectors.toList());
			
			// Get KshmtWorkcondWorkInfo
			List<KshmtWorkcondWorkInfo> listKshmtWorkcondWorkInfo = new ArrayList<>();
			CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				String sqlJdbcWc = "SELECT * FROM KSHMT_WORKCOND_WORKINFO A WHERE A.HIST_ID IN ("
						+ NtsStatement.In.createParamsString(subList) + ")";
				
				try (PreparedStatement statement = this.connection().prepareStatement(sqlJdbcWc)) {
					for (int i = 0; i < subList.size(); i++) {
						statement.setString(i + 1, subList.get(i));
					}
					listKshmtWorkcondWorkInfo.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
								KshmtWorkcondWorkInfoPK pk = new KshmtWorkcondWorkInfoPK();
								pk.setHisId(rec.getString("HIST_ID"));
								pk.setSid(rec.getString("SID"));

								KshmtWorkcondWorkInfo entity = new KshmtWorkcondWorkInfo();
								entity.setPk(pk);
								entity.setWorkingDayWorktype(rec.getString("WORKING_DAY_WORKTYPE"));
								entity.setHolidayWorkWorktype(rec.getString("HOLIDAY_WORK_WORKTYPE"));
								entity.setHolidayWorktype(rec.getString("HOLIDAY_WORKTYPE"));
								entity.setLegalHolidayWorkWorktype(rec.getString("LEGAL_HOLIDAY_WORK_WORKTYPE"));
								entity.setILegalHolidayWorkWorktype(rec.getString("ILLEGAL_HOLIDAY_WORK_WORKTYPE"));
								entity.setPublicHolidayWorkWorktype(rec.getString("PUBLIC_HOLIDAY_WORK_WORKTYPE"));
								entity.setWeekdaysWorktime(rec.getString("WEEKDAYS_WORKTIME"));
								entity.setHolidayWorkWorktime(rec.getString("HOLIDAY_WORK_WORKTIME"));
								entity.setMondayWorkTime(rec.getString("MONDAY_WORKTIME"));
								entity.setTuesdayWorkTime(rec.getString("TUESDAY_WORKTIME"));
								entity.setWednesdayWorkTime(rec.getString("WEDNESDAY_WORKTIME"));
								entity.setThursdayWorkTime(rec.getString("THURSDAY_WORKTIME"));
								entity.setFridayWorkTime(rec.getString("FRIDAY_WORKTIME"));
								entity.setSaturdayWorkTime(rec.getString("SATURDAY_WORKTIME"));
								entity.setSundayWorkTime(rec.getString("SUNDAY_WORKTIME"));
								
								return entity;
							}));
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			});

			// Get KshmtWorkcondWorkTs
			List<KshmtWorkcondWorkTs> listKshmtWorkcondWorkTs = new ArrayList<>();
			CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				String sqlJdbcWc = "SELECT * FROM KSHMT_WORKCOND_WORK_TS A WHERE A.HIST_ID IN ("
						+ NtsStatement.In.createParamsString(subList) + ")";
				try (PreparedStatement statement = this.connection().prepareStatement(sqlJdbcWc)) {
					for (int i = 0; i < subList.size(); i++) {
						statement.setString(i + 1, subList.get(i));
					}
					listKshmtWorkcondWorkTs.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
								KshmtWorkcondWorkTsPK pk = new KshmtWorkcondWorkTsPK();
								pk.setHisId(rec.getString("HIST_ID"));
								pk.setSid(rec.getString("SID"));
								pk.setPerWorkDayAtr(rec.getInt("PER_WORK_DAY_ATR"));

								KshmtWorkcondWorkTs entity = new KshmtWorkcondWorkTs();
								entity.setPk(pk);
								entity.setStartTime1(rec.getInt("START_TIME_1"));
								entity.setStartTime2(rec.getInt("START_TIME_2"));
								entity.setEndTime1(rec.getInt("END_TIME_1"));
								entity.setEndTime2(rec.getInt("END_TIME_2"));

								return entity;
							}));
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			});
			// KshmtWorkcondWorkInfo kshmtWorkcondWorkInfo
			Map<String, List<KshmtWorkcondWorkInfo>> kshmtWorkcondWorkInfoMap = listKshmtWorkcondWorkInfo.stream()
					.collect(Collectors.groupingBy(item -> item.getPk().getHisId()));
			
			// List<KshmtWorkcondWorkTs> listKshmtWorkcondWorkTs;
			Map<String, List<KshmtWorkcondWorkTs>> kshmtWorkcondWorkTsMap = listKshmtWorkcondWorkTs.stream()
					.collect(Collectors.groupingBy(item -> item.getPk().getHisId()));

			result.forEach(item -> {
				item.setKshmtWorkcondWorkInfo(kshmtWorkcondWorkInfoMap.getOrDefault(item.getHistoryId(), Collections.emptyList()));
				item.setListKshmtWorkcondWorkTs(kshmtWorkcondWorkTsMap.getOrDefault(item.getHistoryId(), Collections.emptyList()));
			});

			return result.stream()
					.map(e -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(e)))
					.collect(Collectors.toList());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * add(nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem)
	 */
	@Override
	public void add(WorkingConditionItem item) {
		KshmtWorkcondHistItem entity = new KshmtWorkcondHistItem();
		item.saveToMemento(new JpaWorkingConditionItemSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * update(nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem)
	 */
	@Override
	public void update(WorkingConditionItem item) {
		Optional<KshmtWorkcondHistItem> optEntity = this.queryProxy().find(item.getHistoryId(),
				KshmtWorkcondHistItem.class);

		KshmtWorkcondHistItem entity = optEntity.get();

		item.saveToMemento(new JpaWorkingConditionItemSetMemento(entity));

		this.commandProxy().update(entity);
	}
	
	/**
	 *  Update WorkingConditionItem trong trường hợp category WorkingCondition chia đôi.
	 */
	@Override
	public void updateWorkCond2(WorkingConditionItem item) {
		Optional<KshmtWorkcondHistItem> optEntity = this.queryProxy().find(item.getHistoryId(),
				KshmtWorkcondHistItem.class);

		KshmtWorkcondHistItem entity = optEntity.get();

		item.saveToMemento(new JpaWorkingConditionItem2SetMemento(entity));

		this.commandProxy().update(entity);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * delete(java.lang.String)
	 */
	@Override
	public void delete(String historyId) {
		this.commandProxy().remove(KshmtWorkcondHistItem.class, historyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * getBySidAndHistId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkingConditionItem> getBySid(String employeeId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWorkingCondItem_.sid), employeeId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

		List<KshmtWorkcondHistItem> result = query.getResultList();

		// Check empty
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingConditionItem(
				new JpaWorkingConditionItemGetMemento(result.get(FIRST_ITEM_INDEX))));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * getBySidsAndBaseDate(java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<WorkingConditionItem> getBySidsAndDatePeriod(List<String> employeeIds,
			DatePeriod datePeriod) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

		// select root
		cq.select(root);

		List<KshmtWorkcondHistItem> result = new ArrayList<>();

		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// equal
			lstpredicateWhere.add(root.get(KshmtWorkingCondItem_.sid).in(subList));
			
			lstpredicateWhere.add(criteriaBuilder.not(criteriaBuilder.or(
					criteriaBuilder.lessThan(root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD), datePeriod.start()),
					criteriaBuilder.greaterThan(root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.strD), datePeriod.end()))));
			
			// TODO: Check & request update EAP with new condition
//			lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
//					root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.strD),
//					datePeriod.end()));
//			lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
//					root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD),
//					datePeriod.start()));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// creat query
			TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

			result.addAll(query.getResultList());
		});

		// Check empty
		if (CollectionUtil.isEmpty(result)) {
			return Collections.emptyList();
		}
		
		// exclude select
		List<WorkingConditionItem> data =  result.stream().map(
				entity -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(entity)))
				.collect(Collectors.toList());
		return data;
	}
	
	@Override
	public List<WorkingConditionItem> getBySidsAndDatePeriodNew(List<String> employeeIds,
			DatePeriod datePeriod) {
		//データの取得
		TypedQueryWrapper<Object[]> query = this.queryProxy().query(FIND_BY_SID_AND_PERIOD_WITH_JOIN_NEW, Object[].class);

		List<Object[]> entities = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p ->{
			entities.addAll(query.setParameter("employeeId", p)
							.setParameter("startDate", datePeriod.start())
							.setParameter("endDate", datePeriod.end())
							.getList());
		});
		
		if (entities.isEmpty()) return new ArrayList<>();
		Map<String, List<Object[]>> mapResult = entities.stream().filter(x -> x != null && x[0] != null)
				.collect(Collectors.groupingBy(x -> ((KshmtWorkcondHistItem) x[0]).getHistoryId()));
		return mapResult.entrySet().stream().map(x -> {
			KshmtWorkcondHistItem workCondItem = x.getValue().stream().filter(dt -> dt[0] != null).findFirst()
					.map(dt -> (KshmtWorkcondHistItem) dt[0]).orElse(null);

			if (workCondItem == null) {
				return null;
			}
			return createWorkConditionItem(x.getValue(), workCondItem);
		}).filter(x -> x != null).collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * getBySidAndHistId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkingConditionItem> getBySidAndHistId(String employeeId, String histId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWorkingCondItem_.sid), employeeId));
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWorkingCondItem_.historyId), histId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

		List<KshmtWorkcondHistItem> result = query.getResultList();

		// Check empty
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingConditionItem(
				new JpaWorkingConditionItemGetMemento(result.get(FIRST_ITEM_INDEX))));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#deleteMonthlyPattern(java.lang.String)
	 */
	@Override
	public void deleteMonthlyPattern(String historyId) {
		Optional<KshmtWorkcondHistItem> optEntity = this.queryProxy().find(historyId,
				KshmtWorkcondHistItem.class);
		KshmtWorkcondHistItem entity = optEntity.get();
		
		entity.setMonthlyPattern(null);
		
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#updateMonthlyPattern(java.lang.String, nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode)
	 */
	@Override
	public void updateMonthlyPattern(String historyId, MonthlyPatternCode monthlyPattern) {
		Optional<KshmtWorkcondHistItem> optEntity = this.queryProxy().find(historyId,
				KshmtWorkcondHistItem.class);
		KshmtWorkcondHistItem entity = optEntity.get();
		
		entity.setMonthlyPattern(monthlyPattern.v());
		this.commandProxy().update(entity);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#copyLastMonthlyPatternSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean copyLastMonthlyPatternSetting(String sourceSid, List<String> destSid) {
		// Get items
		Optional<WorkingConditionItem>  optSourceItem = this.getBySid(sourceSid);
		List<KshmtWorkcondHistItem> optDestItem = this.getLastWorkingCondItemEntities(destSid);

		// Check 
		if (!optSourceItem.isPresent() || optDestItem.isEmpty()) {
			// Copy fails
			return false;
		}
		
		// Copy data
		optDestItem.stream().forEach(e -> {
			e.setMonthlyPattern(optSourceItem.get().getMonthlyPattern().get().v());
			// Update
			this.commandProxy().update(e);
		});
		
		// Copy success
		return true;
	}
	
	/**
	 * Gets the last working cond item.
	 *
	 * @param employeeId the employee id
	 * @return the last working cond item
	 */
	private List<KshmtWorkcondHistItem> getLastWorkingCondItemEntities(List<String> employeeId) {
		
		// Check empty
		if (CollectionUtil.isEmpty(employeeId)) {
			return Collections.emptyList();
		}
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
				.createQuery(KshmtWorkcondHistItem.class);

		// root data
		Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);
		
		// select root
		cq.select(root);
		
		List<KshmtWorkcondHistItem> result = new ArrayList<>();

		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// equal
			lstpredicateWhere
					.add(root.get(KshmtWorkingCondItem_.sid).in(subList));
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD),
					GeneralDate.max()));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			
			// create query
			TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

			result.addAll(query.getResultList());
		});

		// exclude select
		return result;
	}

	@Override
	public Map<String, Map<GeneralDate, WorkingConditionItem>> getBySidAndPeriod(Map<String, Set<GeneralDate>> params) {
		//データの取得
		TypedQueryWrapper<Object[]> query = this.queryProxy().query(FIND_BY_SID_AND_PERIOD_WITH_JOIN, Object[].class);

		List<Object[]> entities = new ArrayList<>();
		CollectionUtil.split(params, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p ->{
			Set<GeneralDate> all = p.entrySet().stream().map(c -> c.getValue()).flatMap(Collection::stream).collect(Collectors.toSet());
			GeneralDate min = all.stream().min((d1, d2) -> d1.compareTo(d2)).orElse(null);
			GeneralDate max = all.stream().max((d1, d2) -> d1.compareTo(d2)).orElse(null);
			entities.addAll(query.setParameter("employeeId", p.keySet())
							.setParameter("startDate", min)
							.setParameter("endDate", max)
							.getList());
		});
		
		if (entities.isEmpty()) return new HashMap<>();
		return params.entrySet().stream().collect(Collectors.toMap(p -> p.getKey(), p -> {
			List<Object[]> sub = entities.stream().filter(e -> ((KshmtWorkcondHistItem) e[0]).getSid().equals(p.getKey())).collect(Collectors.toList());
			Map<GeneralDate, WorkingConditionItem> result = new HashMap<>();
			p.getValue().stream().forEach(d -> {
				List<Object[]> data = sub.stream().filter(wc -> {
					KshmtWorkcondHist se = (KshmtWorkcondHist) wc[1];
					return se.getStrD().compareTo(d) <= 0 && se.getEndD().compareTo(d) >= 0;
				}).collect(Collectors.toList());
				
				if(data.isEmpty()){
					return;
				}
				
				KshmtWorkcondHistItem workCondItem = data.stream().filter(dt -> dt[0] != null).findFirst()
						.map(dt -> (KshmtWorkcondHistItem) dt[0]).orElse(null);
				
				if(workCondItem == null){
					return;
				}
				result.put(d, createWorkConditionItem(data, workCondItem));
			});
			
			return result;
		}));
	}
	
	/**
	 * Gets the list with period by sid and date period  
	 * @param employeeId the employee id
	 * @param datePeriod the date period
	 * @return the list
	 */
	@Override
	public WorkingConditionWithDataPeriod getBySidAndPeriodOrderByStrDWithDatePeriod(Map<String,DatePeriod> param,GeneralDate max,GeneralDate min) {
		//データの取得
		TypedQueryWrapper<Object[]> query = this.queryProxy().query(FIND_BY_SID_AND_PERIOD_WITH_JOIN, Object[].class);

		List<Object[]> entities = new ArrayList<>();
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			entities.addAll(query.setParameter("employeeId", p.keySet())
							.setParameter("startDate", min)
							.setParameter("endDate", max)
							.getList());
		});

		Map<DateHistoryItem, WorkingConditionItem> result = new LinkedHashMap<>();
		
		entities.stream().filter(c -> c[0] != null).collect(Collectors.groupingBy(c -> (KshmtWorkcondHistItem) c[0], Collectors.toList()))
			.entrySet().stream().forEach(c -> {
				DatePeriod period = param.get(c.getKey().getSid());
				c.getValue().stream().filter(wc -> (KshmtWorkcondHist) wc[1] != null).findFirst()
					.map(wc -> (KshmtWorkcondHist) wc[1]).ifPresent(wc -> {
					if(period.start().compareTo(wc.getEndD()) <= 0 && period.end().compareTo(wc.getStrD()) >= 0){
						WorkingConditionItem wcItem = createWorkConditionItem(c.getValue(), c.getKey());
						
						GeneralDate strDate = wc.getStrD().compareTo(period.start()) > 0 ? wc.getStrD() : period.start();
						GeneralDate endDate = wc.getEndD().compareTo(period.end()) > 0 ? period.end() : wc.getEndD();
						DateHistoryItem dateItem = new DateHistoryItem(wc.getKshmtWorkingCondPK().getHistoryId(), new DatePeriod(strDate, endDate));
						
						result.put(dateItem, wcItem);
					}
				}); 
			});
				
//		//データの取得
//		TypedQueryWrapper<KshmtWorkcondHistItem> entitys = this.queryProxy().query(FIND_BY_SID_AND_PERIOD_ORDER_BY_STR_D_FOR_MULTI, KshmtWorkcondHistItem.class);
//
//		List<KshmtWorkcondHistItem> a = new ArrayList<>();
//		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p ->{
//			a.addAll(entitys.setParameter("employeeId", p.keySet())
//							.setParameter("startDate", min)
//							.setParameter("endDate", max)
//							.getList()
//							.stream()
//							.filter(tc ->  p.containsKey(tc.getSid())
//										&& p.get(tc.getSid()).start().compareTo(tc.getKshmtWorkingCond().getEndD()) <= 0
//										&& p.get(tc.getSid()).end().compareTo(tc.getKshmtWorkingCond().getStrD()) >= 0)
//							.collect(Collectors.toList()));
//		});
//		
//				
//		Map<DateHistoryItem, WorkingConditionItem> result = new LinkedHashMap<>();
//		//取得したデータの変換
//		a.forEach(c -> {
//			val strDate = c.getKshmtWorkingCond().getStrD().compareTo(param.get(c.getSid()).start())>0?c.getKshmtWorkingCond().getStrD():param.get(c.getSid()).start();
//			val endDate = c.getKshmtWorkingCond().getEndD().compareTo(param.get(c.getSid()).end())<0?param.get(c.getSid()).end():c.getKshmtWorkingCond().getEndD();
//			result.put(new DateHistoryItem(c.getKshmtWorkingCond().getKshmtWorkingCondPK().getHistoryId(), 
//											new DatePeriod(strDate, endDate)), 
//						new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(c)));
//		});
		return new WorkingConditionWithDataPeriod(result);
	}

	private WorkingConditionItem createWorkConditionItem(List<Object[]> source, KshmtWorkcondHistItem main) {
		List<KshmtWorkcondWorkInfo> workInfo = source.stream().filter(dt -> dt[3] != null)
				.map(dt -> (KshmtWorkcondWorkInfo) dt[3]).distinct().collect(Collectors.toList());
		List<KshmtWorkcondWorkTs> workts = source.stream().filter(dt -> dt[4] != null)
				.map(dt -> (KshmtWorkcondWorkTs) dt[4]).distinct().collect(Collectors.toList());
		KshmtWorkcondScheMeth method = source.stream().filter(dt -> dt[2] != null).findFirst()
				.map(dt -> (KshmtWorkcondScheMeth) dt[2]).orElse(null);
		return new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(main, workInfo, workts, method));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * getLastWorkingCondItem(java.util.List)
	 */
	@Override
	public List<WorkingConditionItem> getLastWorkingCondItem(List<String> employeeIds) {
		List<KshmtWorkcondHistItem> result = this.getLastWorkingCondItemEntities(employeeIds);

		// exclude select
		return result.stream().map(
				entity -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#getByListHistoryID(java.util.List)
	 */
	@Override
	public List<WorkingConditionItem> getByListHistoryID(List<String> listHistoryID) {
		List<KshmtWorkcondHistItem> result = this.getByHistIds(listHistoryID);
		// exclude select
		return result.stream().map(entity -> new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#getByListHistoryID(java.util.List)
	 */
	private List<KshmtWorkcondHistItem> getByHistIds(List<String> histIds) {
		// get entity manager
				EntityManager em = this.getEntityManager();
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

				CriteriaQuery<KshmtWorkcondHistItem> cq = criteriaBuilder
						.createQuery(KshmtWorkcondHistItem.class);

				// root data
				Root<KshmtWorkcondHistItem> root = cq.from(KshmtWorkcondHistItem.class);

				// select root
				cq.select(root);

				List<KshmtWorkcondHistItem> result = new ArrayList<>();

				CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
					// add where
					List<Predicate> lstpredicateWhere = new ArrayList<>();

					// equal
					lstpredicateWhere.add(root.get(KshmtWorkingCondItem_.historyId).in(subList));
					
					cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

					// creat query
					TypedQuery<KshmtWorkcondHistItem> query = em.createQuery(cq);

					result.addAll(query.getResultList());
				});

				// Check empty
				if (CollectionUtil.isEmpty(result)) {
					return Collections.emptyList();
				}

				// exclude select
				return result;
	}
	@Override
	public void addAll(List<WorkingConditionItem> items) {
		List<KshmtWorkcondHistItem> entities = new ArrayList<>();
		items.stream().forEach(c ->{
			KshmtWorkcondHistItem entity = new KshmtWorkcondHistItem();
			c.saveToMemento(new JpaWorkingConditionItemSetMemento(entity));
			entities.add(entity);
		});
		
		if(!entities.isEmpty()) {
			this.commandProxy().insertAll(entities);
		}
	}

	@Override
	public void updateAll(List<WorkingConditionItem> items) {
		List<String> histIds = items.stream().map(c -> c.getHistoryId()).collect(Collectors.toList());
		List<KshmtWorkcondHistItem> updateLst = new ArrayList<>();
		if(!histIds.isEmpty()) {
			List<KshmtWorkcondHistItem> entities = this.getByHistIds(histIds);
			items.stream().forEach(c ->{
				KshmtWorkcondHistItem entity = entities.stream().filter(item -> item.getHistoryId().equals(c.getHistoryId())).findFirst().get();
				c.saveToMemento(new JpaWorkingConditionItem2SetMemento(entity));
				updateLst.add(entity);
				
			});
			
			if(!updateLst.isEmpty()) {
				this.commandProxy().updateAll(updateLst);
			}
		}
	}

	@Override
	public void updateAllWorkCond2(List<WorkingConditionItem> items) {
		List<String> histIds = items.stream().map(c -> c.getHistoryId()).collect(Collectors.toList());
		List<KshmtWorkcondHistItem> updateLst = new ArrayList<>();
		if(!histIds.isEmpty()) {
			List<KshmtWorkcondHistItem> entities = this.getByHistIds(histIds);
			items.stream().forEach(c ->{
				KshmtWorkcondHistItem entity = entities.stream().filter(item -> item.getHistoryId().equals(c.getHistoryId())).findFirst().get();
				c.saveToMemento(new JpaWorkingConditionItemSetMemento(entity));
				updateLst.add(entity);
				
			});
			
			if(!updateLst.isEmpty()) {
				this.commandProxy().updateAll(updateLst);
			}
		}
		
	}

	@Override
	public List<WorkingConditionItemWithEnumList> getAllAndEnumByHistIds(List<String> listHistoryID) {
		List<KshmtWorkcondHistItem> entities = this.getByHistIds(listHistoryID);
		List<WorkingConditionItemWithEnumList> result = new ArrayList<>();
		entities.stream().forEach(entity -> {
			Map<String, Object> enums = new HashMap<>();
			WorkingConditionItem item  = new WorkingConditionItem(new JpaWorkingConditionItemGetMemento(entity, enums));
			result.add(new WorkingConditionItemWithEnumList(item, enums));
		});
		return result;
	}

}

