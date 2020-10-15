/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSet_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaWorkMonthlySettingRepository.
 */
@Stateless
public class JpaWorkMonthlySettingRepository extends JpaRepository
		implements WorkMonthlySettingRepository {

	/** The Constant INDEX_ONE. */
	public static final int INDEX_ONE = 1;

	private final String SELECT_FROM_WORKMONTH_SET = "SELECT w FROM KscmtWorkMonthSet w";

	private final String SELECT_BY_CID = SELECT_FROM_WORKMONTH_SET
			+ " WHERE w.kscmtWorkMonthSetPK.cid = :cid";

	private final String SELECT_BY_PERIOD = SELECT_BY_CID
			+ " AND w.kscmtWorkMonthSetPK.mPatternCd = :mPatternCd"
			+ " AND w.kscmtWorkMonthSetPK.ymdM >= :startDate "
			+ " AND w.kscmtWorkMonthSetPK.ymdM <= :endDate"
			+ " ORDER BY w.kscmtWorkMonthSetPK.ymdM ASC";

	private final String SELECT_BY_YEAR = SELECT_BY_CID
			+ " AND w.kscmtWorkMonthSetPK.mPatternCd = :mPatternCd"
			+ " AND  EXTRACT(Year FROM w.kscmtWorkMonthSetPK.ymdM)  = :year "
			+ " ORDER BY w.kscmtWorkMonthSetPK.ymdM ASC";

	private static final String DELETE_BY_WORK_MONTHLY_ID_AND_DATE = "DELETE from KscmtWorkMonthSet c "
			+ " WHERE c.kscmtWorkMonthSetPK.cid = :cid"
			+ " AND c.kscmtWorkMonthSetPK.mPatternCd = :mPatternCd"
			+ " AND c.kscmtWorkMonthSetPK.ymdM = :ymdM";
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#addAll(java.util.List)
	 */
	@Override
	public void addAll(List<WorkMonthlySetting> workMonthlySettings) {
		this.commandProxy().insertAll( workMonthlySettings.stream()
				.map(domain -> this.toEntity(domain)).collect(Collectors.toList()));
		
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#updateAll(java.util.List)
	 */
	@Override
	public void updateAll(List<WorkMonthlySetting> workMonthlySettings) {
		
		// get by input work monthly setting
		List<KscmtWorkMonthSet> entitys = this.toEntityUpdateAll(workMonthlySettings);
		
		// convert to map entity
		Map<GeneralDate, KscmtWorkMonthSet> mapEntity = entitys.stream()
				.collect(Collectors.toMap((entity) -> {
					return entity.getKscmtWorkMonthSetPK().getYmdM();
				}, Function.identity()));
		
		// update all entity
		this.commandProxy().updateAll(workMonthlySettings.stream().map(domain -> {
			KscmtWorkMonthSet entity = new KscmtWorkMonthSet();
			if (mapEntity.containsKey(domain.getYmdk())) {
				entity = mapEntity.get(domain.getYmdk());
			}
			domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
			return entity;
		}).collect(Collectors.toList()));
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#findById(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkMonthlySetting> findById(String companyId, String monthlyPatternCode,
			GeneralDate baseDate) {
		return this.queryProxy()
				.find(new KscmtWorkMonthSetPK(companyId, monthlyPatternCode,
						baseDate), KscmtWorkMonthSet.class)
				.map(c -> this.toDomain(c));
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#findByStartEndDate(java.lang.String,
	 * java.lang.String, nts.arc.time.GeneralDate, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<WorkMonthlySetting> findByStartEndDate(String companyId, String monthlyPatternCode,
			GeneralDate startDate, GeneralDate endDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KscmtWorkMonthSet> cq = criteriaBuilder.createQuery(KscmtWorkMonthSet.class);

		// root data
		Root<KscmtWorkMonthSet> root = cq.from(KscmtWorkMonthSet.class);

		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.cid),
				companyId));
		
		// equal monthly pattern code
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
				.get(KscmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));
		
		// greater than or equal start date
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM),
				startDate));
		
		// less than or equal end date
		lstpredicateWhere.add(criteriaBuilder.lessThan(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM),
				endDate));
				
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// order by ymdk id asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM)));

		// create query
		TypedQuery<KscmtWorkMonthSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the work monthly setting
	 */
	private WorkMonthlySetting toDomain(KscmtWorkMonthSet entity) {
		return new WorkMonthlySetting(new JpaWorkMonthlySettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwmmt work month set
	 */
	private KscmtWorkMonthSet toEntity(WorkMonthlySetting domain){
		KscmtWorkMonthSet entity = new KscmtWorkMonthSet();
		val cd = AppContexts.user().contractCode();
		domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
		entity.setContractCd(cd);
		return entity;
	}
	
	/**
	 * To entity update all.
	 *
	 * @param workMonthlySettings the work monthly settings
	 * @return the list
	 */
	private List<KscmtWorkMonthSet> toEntityUpdateAll(List<WorkMonthlySetting> workMonthlySettings){
		
		// check exist data by input
		if(CollectionUtil.isEmpty(workMonthlySettings)){
			return new ArrayList<>();
		}
		
		// get company id
		String companyId = workMonthlySettings.size() > 0 ? workMonthlySettings.stream().findFirst().get().getCompanyId().v() : null;
		
		// get monthly pattern code
		String monthlyPatternCode = workMonthlySettings.size() > 0 ? workMonthlySettings.stream().findFirst().get().getMonthlyPatternCode().v() : null;
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KscmtWorkMonthSet> cq = criteriaBuilder.createQuery(KscmtWorkMonthSet.class);

		// root data
		Root<KscmtWorkMonthSet> root = cq.from(KscmtWorkMonthSet.class);

		// select root
		cq.select(root);
		
		List<KscmtWorkMonthSet> resultList = new ArrayList<>();
		List<GeneralDate> collect = workMonthlySettings.stream().map(setting -> setting.getYmdk()).collect(Collectors.toList());
		
		CollectionUtil.split(collect, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// equal company id
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.cid),
					companyId));
			
			// equal monthly pattern code
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
					.get(KscmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));
			// in base date data list
			lstpredicateWhere.add(criteriaBuilder.and(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
					.get(KscmtWorkMonthSetPK_.ymdM).in(splitData)));
			
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			
			// order by ymdk id asc
			cq.orderBy(criteriaBuilder.asc(
					root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM)));
			
			resultList.addAll(em.createQuery(cq).getResultList());
		});

		
		return resultList;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#findByYMD(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkMonthlySetting> findByYMD(String companyId, String monthlyPatternCode,
			List<GeneralDate> baseDates) {

		// check exist data by input
		if(CollectionUtil.isEmpty(baseDates)){
			return new ArrayList<>();
		}
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KscmtWorkMonthSet> cq = criteriaBuilder.createQuery(KscmtWorkMonthSet.class);

		// root data
		Root<KscmtWorkMonthSet> root = cq.from(KscmtWorkMonthSet.class);

		// select root
		cq.select(root);
		
		List<KscmtWorkMonthSet> resultList = new ArrayList<>();
		CollectionUtil.split(baseDates, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// equal company id
			lstpredicateWhere.add(criteriaBuilder.equal(
			        root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.cid),
					companyId));
			
			// equal monthly pattern code
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
					.get(KscmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));
			// in base date data list
			lstpredicateWhere.add(criteriaBuilder.and(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
					.get(KscmtWorkMonthSetPK_.ymdM).in(splitData)));
			
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// order by ymdk id asc
			cq.orderBy(criteriaBuilder.asc(
					root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM)));

			resultList.addAll(em.createQuery(cq).getResultList());
		});

		// exclude select
		return resultList.stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String monthlyPatternCode) {
		this.commandProxy().removeAll(this.toEntityRemove(companyId, monthlyPatternCode));
	}

	@Override
	public List<WorkMonthlySetting> findByPeriod(String companyId, String monthlyPatternCode, DatePeriod datePeriod) {
		return this.queryProxy().query(SELECT_BY_PERIOD, KscmtWorkMonthSet.class)
				.setParameter("cid", companyId)
				.setParameter("mPatternCd", monthlyPatternCode)
				.setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end())
				.getList(x -> this.toDomain(x));
	}

	@Override
	public Boolean exists(String companyId, String monthlyPatternCode, GeneralDate date) {
		Optional<KscmtWorkMonthSet> kscmtWorkMonthSet = this.queryProxy().find(new KscmtWorkMonthSetPK(companyId, monthlyPatternCode, date), KscmtWorkMonthSet.class);
		return kscmtWorkMonthSet.isPresent();
	}

	@Override
	public void add(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().insert(toEntity(workMonthlySetting));
	}

	@Override
	public void update(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().update(toEntity(workMonthlySetting));
	}

	@Override
	public List<WorkMonthlySetting> findByYear(String companyId, String monthlyPatternCode, int year) {
		return this.queryProxy().query(SELECT_BY_YEAR, KscmtWorkMonthSet.class)
				.setParameter("cid", companyId)
				.setParameter("mPatternCd", monthlyPatternCode)
				.setParameter("year", year)
				.getList(x -> this.toDomain(x));
	}
	@Override
	public void deleteWorkMonthlySettingById(String companyId,String mPatternCd,GeneralDate date) {
		this.getEntityManager().createQuery(DELETE_BY_WORK_MONTHLY_ID_AND_DATE, KscmtWorkMonthSet.class)
				.setParameter("cid", companyId)
				.setParameter("mPatternCd", mPatternCd)
				.setParameter("ymdM", date).executeUpdate();
	}

	/**
	 * To entity remove.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the list
	 */
	private List<KscmtWorkMonthSet> toEntityRemove(String companyId, String monthlyPatternCode){
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KscmtWorkMonthSet> cq = criteriaBuilder.createQuery(KscmtWorkMonthSet.class);

		// root data
		Root<KscmtWorkMonthSet> root = cq.from(KscmtWorkMonthSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.cid),
				companyId));

		// equal monthly pattern code
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK)
				.get(KscmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by ymdk id asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KscmtWorkMonthSet_.kscmtWorkMonthSetPK).get(KscmtWorkMonthSetPK_.ymdM)));

		// create query
		TypedQuery<KscmtWorkMonthSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

	
}
