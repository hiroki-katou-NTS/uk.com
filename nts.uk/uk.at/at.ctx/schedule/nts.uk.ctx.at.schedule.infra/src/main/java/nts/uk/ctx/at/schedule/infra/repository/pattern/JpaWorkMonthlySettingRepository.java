/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import java.math.BigDecimal;
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

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSet_;

/**
 * The Class JpaWorkMonthlySettingRepository.
 */
@Stateless
public class JpaWorkMonthlySettingRepository extends JpaRepository
		implements WorkMonthlySettingRepository {

	/** The Constant INDEX_ONE. */
	public static final int INDEX_ONE = 1;
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.pattern
	 * .work.WorkMonthlySetting)
	 */
	@Override
	public void add(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().insert(this.toEntity(workMonthlySetting));
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#addAll(java.util.List)
	 */
	@Override
	public void addAll(List<WorkMonthlySetting> workMonthlySettings) {
		List<KwmmtWorkMonthSet> entitys = workMonthlySettings.stream()
				.map(domain -> this.toEntity(domain)).collect(Collectors.toList());
		entitys.forEach(entity->{
			System.out.println(entity.getKwmmtWorkMonthSetPK().getYmdK());
		});
		this.commandProxy().insertAll(entitys);
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * pattern.work.WorkMonthlySetting)
	 */
	@Override
	public void update(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().update(this.toEntityUpdate(workMonthlySetting));
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
		List<KwmmtWorkMonthSet> entitys = this.toEntityUpdateAll(workMonthlySettings);
		
		// convert to map entity
		Map<BigDecimal, KwmmtWorkMonthSet> mapEntity = entitys.stream()
				.collect(Collectors.toMap((entity) -> {
					return entity.getKwmmtWorkMonthSetPK().getYmdK();
				}, Function.identity()));
		
		// update all entity
		this.commandProxy().updateAll(workMonthlySettings.stream().map(domain -> {
			KwmmtWorkMonthSet entity = new KwmmtWorkMonthSet();
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
			int baseDate) {
		return this.queryProxy()
				.find(new KwmmtWorkMonthSetPK(companyId, monthlyPatternCode,
						BigDecimal.valueOf(baseDate)), KwmmtWorkMonthSet.class)
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
			int startDate, int endDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KwmmtWorkMonthSet> cq = criteriaBuilder.createQuery(KwmmtWorkMonthSet.class);

		// root data
		Root<KwmmtWorkMonthSet> root = cq.from(KwmmtWorkMonthSet.class);

		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.cid),
				companyId));
		
		// equal monthly pattern code
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK)
				.get(KwmmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));
		
		// greater than or equal start date
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.ymdK),
				BigDecimal.valueOf(startDate)));
		
		// less than or equal end date
		lstpredicateWhere.add(criteriaBuilder.lessThan(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.ymdK),
				BigDecimal.valueOf(endDate)));
				
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// order by ymdk id asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.ymdK)));

		// create query
		TypedQuery<KwmmtWorkMonthSet> query = em.createQuery(cq);

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
	private WorkMonthlySetting toDomain(KwmmtWorkMonthSet entity) {
		return new WorkMonthlySetting(new JpaWorkMonthlySettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwmmt work month set
	 */
	private KwmmtWorkMonthSet toEntity(WorkMonthlySetting domain){
		KwmmtWorkMonthSet entity = new KwmmtWorkMonthSet();
		domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwmmt work month set
	 */
	private KwmmtWorkMonthSet toEntityUpdate(WorkMonthlySetting domain){
		
		Optional<KwmmtWorkMonthSet> optionalEntity = this.queryProxy()
				.find(new KwmmtWorkMonthSetPK(domain.getCompanyId().v(),
						domain.getMonthlyPatternCode().v(), domain.getYmdk()),
						KwmmtWorkMonthSet.class);
		
		KwmmtWorkMonthSet entity = optionalEntity.get();
		domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
		return entity;
	}

	/**
	 * To entity update all.
	 *
	 * @param workMonthlySettings the work monthly settings
	 * @return the list
	 */
	private List<KwmmtWorkMonthSet> toEntityUpdateAll(List<WorkMonthlySetting> workMonthlySettings){
		
		// check exist data by input
		if(CollectionUtil.isEmpty(workMonthlySettings)){
			return new ArrayList<>();
		}
		
		// get company id
		String companyId = workMonthlySettings.get(INDEX_ONE).getCompanyId().v();
		
		// get monthly pattern code
		String monthlyPatternCode = workMonthlySettings.get(INDEX_ONE).getMonthlyPatternCode().v();
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWMMT_WORK_MONTH_SET (KwmmtWorkMonthSet SQL)
		CriteriaQuery<KwmmtWorkMonthSet> cq = criteriaBuilder.createQuery(KwmmtWorkMonthSet.class);

		// root data
		Root<KwmmtWorkMonthSet> root = cq.from(KwmmtWorkMonthSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.cid),
				companyId));

		// equal monthly pattern code
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK)
				.get(KwmmtWorkMonthSetPK_.mPatternCd), monthlyPatternCode));

		// in base date data list
		lstpredicateWhere.add(criteriaBuilder.and(root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK)
				.get(KwmmtWorkMonthSetPK_.ymdK).in(workMonthlySettings.stream()
						.map(setting -> setting.getYmdk()).collect(Collectors.toList()))));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by ymdk id asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KwmmtWorkMonthSet_.kwmmtWorkMonthSetPK).get(KwmmtWorkMonthSetPK_.ymdK)));

		// create query
		TypedQuery<KwmmtWorkMonthSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
}
