/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK_;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkingConditionRepository.
 */
@Stateless
public class JpaWorkingConditionRepository extends JpaRepository implements WorkingConditionRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getAllWokingCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getBySid(String companyId, String sId) {

		List<KshmtWorkingCond> result = this.findBy(companyId, sId, null);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getBySid(java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getBySid(String sId) {

		List<KshmtWorkingCond> result = this.findBy(null, sId, null);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getByHistoryId(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<WorkingCondition> getByHistoryId(String historyId) {
		List<KshmtWorkingCond> result = this.findBy(null, null, historyId);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getBySidAndStandardDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid), employeeId));
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KshmtWorkingCond_.strD), baseDate));
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KshmtWorkingCond_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		List<KshmtWorkingCond> result = query.getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/**
	 * Adds the.
	 *
	 * @param entities
	 *            the entities
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#add(
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	private void add(List<KshmtWorkingCond> entities) {
		this.commandProxy().insertAll(entities);
		this.getEntityManager().flush();
	}

	/**
	 * Delete all.
	 *
	 * @param entities
	 *            the entities
	 */
	private void deleteAll(List<KshmtWorkingCond> entities) {
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#save
	 * (nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	@Transactional
	public void save(WorkingCondition workingCondition) {
		List<KshmtWorkingCond> entities = this.findBy(workingCondition.getCompanyId(), workingCondition.getEmployeeId(),
		null);
		
		List<KshmtWorkingCond> newWorkingCondition = new ArrayList<>(entities);

		workingCondition.saveToMemento(new JpaWorkingConditionSetMemento(newWorkingCondition));
		
		this.add(newWorkingCondition.stream()
				.filter(item -> {
					int index = entities.indexOf(item);
					if (index == -1) {
						return true;
					}				
					
					KshmtWorkingCond oldItem = entities.get(index);
					if (oldItem.getStrD().equals(item.getStrD()) && oldItem.getEndD().equals(item.getEndD())) {
						return false;
					}
					return true;
				})
				.collect(Collectors.toList()));
		
		this.deleteAll(entities.stream().filter(item ->  !newWorkingCondition.contains(item)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String employeeId) {
		List<KshmtWorkingCond> entities = this.findBy(null, employeeId, null);
		this.commandProxy().removeAll(entities);
	}

	/**
	 * Find by.
	 *
	 * @param companyId
	 *            the company id
	 * @param sId
	 *            the s id
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	private List<KshmtWorkingCond> findBy(String companyId, String sId, String historyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		if (companyId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), companyId));
		}

		if (sId != null) {
			lstpredicateWhere.add(criteriaBuilder
					.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid), sId));
		}

		if (historyId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.historyId), historyId));
		}

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// Order by start date
		cq.orderBy(criteriaBuilder.desc(root.get(KshmtWorkingCond_.strD)));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		return query.getResultList();
	}
	

	/**
	 * Find by.
	 *
	 * @param companyId
	 *            the company id
	 * @param sId
	 *            the s id
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	private Map<String, List<KshmtWorkingCond>> findBy(String cid, List<String> sids) {
		// Check exist
		if (CollectionUtil.isEmpty(sids)) {
			return new HashMap<>();
		}
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);
		
		List<KshmtWorkingCond> result =  new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			
			// eq company id
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), cid));
			lstpredicateWhere.add(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
					.get(KshmtWorkingCondPK_.sid).in(subList));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// creat query
			TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);
			
			result.addAll(query.getResultList());
			
		});

		return result.stream()
				.collect(Collectors.groupingBy(entity -> entity.getKshmtWorkingCondPK().getSid()));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository
	 * #getBySidAndStandardDate(java.lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkingCondition> getBySidAndStandardDate(String companyId, String employeeId,
			GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWorkingCond_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid), employeeId));
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KshmtWorkingCond_.strD), baseDate));
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KshmtWorkingCond_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		List<KshmtWorkingCond> result = query.getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#getBySids(java.util.List)
	 */
	@Override
	public List<WorkingCondition> getBySidsAndDatePeriod(List<String> employeeIds, DatePeriod datePeriod) {
		
		// Check exist
		if (CollectionUtil.isEmpty(employeeIds)) {
			return Collections.emptyList();
		}
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);
		
		List<KshmtWorkingCond> result =  new ArrayList<>();
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			
			// eq company id
			lstpredicateWhere.add(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
					.get(KshmtWorkingCondPK_.sid).in(subList));
			lstpredicateWhere.add(criteriaBuilder.not(criteriaBuilder.or(
					criteriaBuilder.lessThan(root.get(KshmtWorkingCond_.endD), datePeriod.start()),
					criteriaBuilder.greaterThan(root.get(KshmtWorkingCond_.strD), datePeriod.end()))));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// creat query
			TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);
			
			result.addAll(query.getResultList());
		});

		List<WorkingCondition> data = result.parallelStream()
				.collect(Collectors.groupingBy(entity -> entity.getKshmtWorkingCondPK().getSid()))
				.values().parallelStream()
				.map(item -> new WorkingCondition(new JpaWorkingConditionGetMemento(item)))
				.collect(Collectors.toList());
		 return data;
	}
	
	@Override
	public List<WorkingCondition> getBySidsAndDatePeriodNew(List<String> employeeIds, DatePeriod datePeriod) {
		// Check exist
		if (CollectionUtil.isEmpty(employeeIds)) {
			return Collections.emptyList();
		}

		List<WorkingCondition> result = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "select * from KSHMT_WORKING_COND h" + " where h.SID in ("
					+ NtsStatement.In.createParamsString(subList) + ")" + " and h.START_DATE <= ?"
					+ " and h.END_DATE >= ?" + " and h.CID >= ?";

			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {

				int i = 0;
				for (; i < subList.size(); i++) {
					stmt.setString(1 + i, subList.get(i));
				}

				stmt.setDate(1 + i, Date.valueOf(datePeriod.end().localDate()));
				stmt.setDate(2 + i, Date.valueOf(datePeriod.start().localDate()));
				stmt.setString(3 + i, companyId);

				result.addAll(new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					List<DateHistoryItem> lstHist = new ArrayList<>();
					lstHist.add(new DateHistoryItem(rec.getString("HIST_ID"),
							new DatePeriod(rec.getGeneralDate("START_DATE"), rec.getGeneralDate("END_DATE"))));
					WorkingCondition ent = new WorkingCondition(rec.getString("CID"), rec.getString("SID"), lstHist);
					return ent;
				}));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		});

		Map<String, List<WorkingCondition>> mapGroupSId = result.stream()
				.collect(Collectors.groupingBy(x -> x.getEmployeeId()));
		return mapGroupSId.entrySet().stream().map(x -> {
			WorkingCondition wC = new WorkingCondition(companyId, x.getKey(),
					x.getValue().stream().flatMap(y -> y.getDateHistoryItem().stream()).collect(Collectors.toList()));
			return wC;
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#getBySidsAndDatePeriod(java.util.List)
	 */
	@Override
	public List<WorkingCondition> getBySids(List<String> employeeIds,GeneralDate baseDate) {
		// Check exist
				if (CollectionUtil.isEmpty(employeeIds)) {
					return Collections.emptyList();
				}
						
				// get entity manager
				EntityManager em = this.getEntityManager();
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

				CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

				// root data
				Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

				// select root
				cq.select(root);
				
				List<KshmtWorkingCond> result =  new ArrayList<>();
				
				CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
					// add where
					List<Predicate> lstpredicateWhere = new ArrayList<>();
					
					// eq company id
					lstpredicateWhere.add(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
							.get(KshmtWorkingCondPK_.sid).in(subList));
					lstpredicateWhere.add(criteriaBuilder.not(criteriaBuilder.or(
							criteriaBuilder.lessThan(root.get(KshmtWorkingCond_.endD), baseDate),
							criteriaBuilder.greaterThan(root.get(KshmtWorkingCond_.strD), baseDate))));


					// set where to SQL
					cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

					// creat query
					TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);
					
					result.addAll(query.getResultList());
				});

				return result.stream()
						.collect(Collectors.groupingBy(entity -> entity.getKshmtWorkingCondPK().getSid()))
						.values().stream()
						.map(item -> new WorkingCondition(new JpaWorkingConditionGetMemento(item)))
						.collect(Collectors.toList());
	}

	@Override
	public List<WorkingCondition> getBySidsAndCid(List<String> employeeIds, GeneralDate baseDate, String cid) {
		// Check exist
		if (CollectionUtil.isEmpty(employeeIds)) {
			return Collections.emptyList();
		}
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);
		
		List<KshmtWorkingCond> result =  new ArrayList<>();
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			
			// eq company id
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), cid));
			lstpredicateWhere.add(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
					.get(KshmtWorkingCondPK_.sid).in(subList));
			lstpredicateWhere.add(criteriaBuilder.not(criteriaBuilder.or(
					criteriaBuilder.lessThan(root.get(KshmtWorkingCond_.endD), baseDate),
					criteriaBuilder.greaterThan(root.get(KshmtWorkingCond_.strD), baseDate))));


			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// creat query
			TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);
			
			result.addAll(query.getResultList());
		});

		return result.stream()
				.collect(Collectors.groupingBy(entity -> entity.getKshmtWorkingCondPK().getSid()))
				.values().stream()
				.map(item -> new WorkingCondition(new JpaWorkingConditionGetMemento(item)))
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkingCondition> getBySidsAndCid(String cid, List<String> sIds) {
		// Check exist
		if (CollectionUtil.isEmpty(sIds)) {
			return Collections.emptyList();
		}
				
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);
		
		List<KshmtWorkingCond> result =  new ArrayList<>();
		
		CollectionUtil.split(sIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			
			// eq company id
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), cid));
			lstpredicateWhere.add(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
					.get(KshmtWorkingCondPK_.sid).in(subList));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			// Order by start date DESC
			cq.orderBy(criteriaBuilder.desc(root.get(KshmtWorkingCond_.strD)));

			// creat query
			TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);
			
			result.addAll(query.getResultList());
		});

		return result.stream()
				.collect(Collectors.groupingBy(entity -> entity.getKshmtWorkingCondPK().getSid()))
				.values().stream()
				.map(item -> new WorkingCondition(new JpaWorkingConditionGetMemento(item)))
				.collect(Collectors.toList());
	}

	@Override
	public void saveAll(List<WorkingCondition> workingConditions) {
		String cid = workingConditions.get(0).getCompanyId();
		List<KshmtWorkingCond> insertLst = new ArrayList<>();
		List<KshmtWorkingCond> deleteLst = new ArrayList<>();
		List<String> sids = workingConditions.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		Map<String, List<KshmtWorkingCond>> entityMaps = this.findBy(cid, sids);
		
		workingConditions.stream().forEach(c ->{
			List<KshmtWorkingCond> entities = entityMaps.get(c.getEmployeeId());
			List<KshmtWorkingCond> newWorkingCondition = new ArrayList<>(entities);
			c.saveToMemento(new JpaWorkingConditionSetMemento(newWorkingCondition));
			insertLst.addAll(newWorkingCondition.stream()
					.filter(item -> {
						int index = entities.indexOf(item);
						if (index == -1) {
							return true;
						}				
						
						KshmtWorkingCond oldItem = entities.get(index);
						if (oldItem.getStrD().equals(item.getStrD()) && oldItem.getEndD().equals(item.getEndD())) {
							return false;
						}
						return true;
					}).collect(Collectors.toList()));

			deleteLst.addAll(entities.stream().filter(item ->  !newWorkingCondition.contains(item)).collect(Collectors.toList()));
		});
		
		if(!insertLst.isEmpty()) {
			this.commandProxy().insertAll(insertLst);
		}
		
		if(!deleteLst.isEmpty()) {
			this.commandProxy().removeAll(deleteLst);
		}
	}

	@Override
	public void insert(WorkingCondition workingCondition, WorkingConditionItem workingConditionItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkingCondition workingCondition, WorkingConditionItem workingConditionItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String empID, String histID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String empID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WorkingCondition> getWorkingCondition(String companyID, String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkingCondition> getWorkingConditionByListEmpID(String companyID, List<String> lstEmpID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkingConditionItem> getWorkingConditionItem(String histID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkingConditionItem> getWorkingConditionItemByListHistID(List<String> listHistID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkingConditionItem> getWorkingConditionItemByCompanyIDAndDate(String companyID, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkingConditionItem> getWorkingConditionItemByEmpIDAndDate(String companyID, GeneralDate ymd,
			String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkingConditionItem> getWorkingConditionItemByLstEmpIDAndDate(String companyID, GeneralDate ymd,
			List<String> empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkingConditionItemWithPeriod> getWorkingConditionItemWithPeriod(String companyID,
			List<String> lstEmpID, DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		return null;
	}

}
