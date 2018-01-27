package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHistItem;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHistItem_;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHist_;

@Stateless
public class JpaEmploymentHistoryItemRepository extends JpaRepository implements EmploymentHistoryItemRepository {

	private static String SEL_HIS_ITEM = " SELECT a.bsymtEmploymentPK.code ,a.name FROM BsymtEmployment a"
			+ " INNER JOIN BsymtEmploymentHist h" 
			+ " ON a.bsymtEmploymentPK.cid = h.companyId"
			+ " INNER JOIN BsymtEmploymentHistItem i"
			+ " ON  h.hisId = i.hisId " 
			+ " AND h.sid  = i.sid"
			+ " AND a.bsymtEmploymentPK.code =  i.empCode" 
			+ " WHERE h.sid =:sid" + " AND h.strDate <= :date"
			+ " AND h.endDate >= :date " 
			+ " AND a.bsymtEmploymentPK.cid =:companyId";

	
	@Override
	public Optional<EmploymentInfo> getDetailEmploymentHistoryItem(String companyId, String sid, GeneralDate date) {
		Optional<EmploymentInfo> employee = this.queryProxy().query(SEL_HIS_ITEM, Object[].class)
				.setParameter("sid", sid).setParameter("date", date).setParameter("companyId", companyId)
				.getSingle(c -> toDomainEmployee(c));
		return employee;
	}
	
	@Override
	public Optional<EmploymentHistoryItem> getByHistoryId(String historyId) {
		Optional<BsymtEmploymentHistItem> hiDataOpt = this.queryProxy().find(historyId, BsymtEmploymentHistItem.class);
		if (hiDataOpt.isPresent()) {
			BsymtEmploymentHistItem ent = hiDataOpt.get();
			return Optional.of( EmploymentHistoryItem.createFromJavaType(ent.hisId, ent.sid, ent.empCode, ent.salarySegment));
		}
		return null;
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtEmploymentHistItem toEntity(EmploymentHistoryItem domain) {
		return new BsymtEmploymentHistItem(domain.getHistoryId(), domain.getEmployeeId(),
				domain.getEmploymentCode().v(), domain.getSalarySegment() !=null? domain.getSalarySegment().value: 0);
	}

	private EmploymentInfo toDomainEmployee(Object[] entity) {
		EmploymentInfo emp = new EmploymentInfo();
		if (entity[0] != null) {
			emp.setEmploymentCode(entity[0].toString());
		}
		if (entity[1] != null) {
			emp.setEmploymentName(entity[1].toString());
		}
		return emp;
	}

	/**
	 * Update entity from domain
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmploymentHistoryItem domain, BsymtEmploymentHistItem entity) {
		if (domain.getEmploymentCode() != null && !domain.getEmploymentCode().v().equals("")){
			entity.empCode = domain.getEmploymentCode().v();
		}
		if (domain.getSalarySegment() != null){
			entity.salarySegment = domain.getSalarySegment().value;
		}
	}

	@Override
	public void add(EmploymentHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmploymentHistoryItem domain) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(domain.getHistoryId(),
				BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		updateEntity(domain, existItem.get());
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(histId, BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		this.commandProxy().remove(BsymtEmploymentHistItem.class, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<EmploymentHistoryItem> searchEmployee(GeneralDate baseDate,
			List<String> employmentCodes) {
		
		// check not data input
		if (CollectionUtil.isEmpty(employmentCodes)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<BsymtEmploymentHistItem> cq = criteriaBuilder
				.createQuery(BsymtEmploymentHistItem.class);

		// root data
		Root<BsymtEmploymentHistItem> root = cq.from(BsymtEmploymentHistItem.class);

		// select root
		cq.select(root);
		
		// Split query.
		List<BsymtEmploymentHistItem> resultList = new ArrayList<>();
		
		CollectionUtil.split(employmentCodes, 1000, (subList) -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// employment in data employment
			lstpredicateWhere
					.add(criteriaBuilder.and(root.get(BsymtEmploymentHistItem_.empCode).in(subList)));

			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.strDate), baseDate));

			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.endDate), baseDate));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// create query
			TypedQuery<BsymtEmploymentHistItem> query = em.createQuery(cq);
			resultList.addAll(query.getResultList());
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<EmploymentHistoryItem> searchEmployee(GeneralDate baseDate, List<String> employeeIds, 
			List<String> employmentCodes) {
		if (CollectionUtil.isEmpty(employeeIds) || CollectionUtil.isEmpty(employmentCodes)) {
			return new ArrayList<>();
		}
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<BsymtEmploymentHistItem> cq = criteriaBuilder
				.createQuery(BsymtEmploymentHistItem.class);

		// root data
		Root<BsymtEmploymentHistItem> root = cq.from(BsymtEmploymentHistItem.class);

		// select root
		cq.select(root);

		List<BsymtEmploymentHistItem> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, employeeSubList -> {
			CollectionUtil.split(employmentCodes, 1000, employmentSubList -> {
				// add where
				List<Predicate> lstpredicateWhere = new ArrayList<>();

				// employment in data employment
				lstpredicateWhere
						.add(criteriaBuilder.and(root.get(BsymtEmploymentHistItem_.empCode).in(employmentSubList)));

				// employee id in data employee id
				lstpredicateWhere
						.add(criteriaBuilder.and(root.get(BsymtEmploymentHistItem_.sid).in(employeeSubList)));

				// start date <= base date
				lstpredicateWhere.add(criteriaBuilder
						.lessThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
								.get(BsymtEmploymentHist_.strDate), baseDate));

				// endDate >= base date
				lstpredicateWhere.add(criteriaBuilder
						.greaterThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
								.get(BsymtEmploymentHist_.endDate), baseDate));

				// set where to SQL
				cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

				// create query
				TypedQuery<BsymtEmploymentHistItem> query = em.createQuery(cq);
				resultList.addAll(query.getResultList());
			});
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository
	 * #searchEmploymentOfSids(java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmploymentHistoryItem> searchEmploymentOfSids(List<String> employeeIds,
			GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<BsymtEmploymentHistItem> cq = criteriaBuilder
				.createQuery(BsymtEmploymentHistItem.class);

		// root data
		Root<BsymtEmploymentHistItem> root = cq.from(BsymtEmploymentHistItem.class);

		// select root
		cq.select(root);
		if (CollectionUtil.isEmpty(employeeIds)) {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.strDate), baseDate));
	
			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.endDate), baseDate));
	
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
	
			// create query
			TypedQuery<BsymtEmploymentHistItem> query = em.createQuery(cq);
	
			// exclude select
			return query.getResultList().stream().map(category -> toDomain(category))
					.collect(Collectors.toList());
		}
		
		// Split employee ids.
		List<BsymtEmploymentHistItem> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			

			// employee id in data employee id
			lstpredicateWhere
					.add(criteriaBuilder.and(root.get(BsymtEmploymentHistItem_.sid).in(subList)));

			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.strDate), baseDate));
	
			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(BsymtEmploymentHistItem_.bsymtEmploymentHist)
							.get(BsymtEmploymentHist_.endDate), baseDate));
			
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// create query
			TypedQuery<BsymtEmploymentHistItem> query = em.createQuery(cq);
			resultList.addAll(query.getResultList());
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the employment history
	 */
	private EmploymentHistoryItem toDomain(BsymtEmploymentHistItem entity) {
		return EmploymentHistoryItem.createFromJavaType(entity.hisId, entity.sid, entity.empCode, entity.salarySegment);
	}

}
