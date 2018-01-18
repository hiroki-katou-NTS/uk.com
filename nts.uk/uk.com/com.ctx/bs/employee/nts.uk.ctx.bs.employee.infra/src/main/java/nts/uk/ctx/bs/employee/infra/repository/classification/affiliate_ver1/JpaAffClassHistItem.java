/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

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
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.BsymtAffClassHistItem_Ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.BsymtAffClassHistItem_Ver1_;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.BsymtAffClassHistory_Ver1_;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaAffClassHistItem extends JpaRepository implements AffClassHistItemRepository_ver1 {

	@Override
	public Optional<AffClassHistItem_ver1> getByHistoryId(String historyId) {
		Optional<BsymtAffClassHistItem_Ver1> optionData = this.queryProxy().find(historyId,
				BsymtAffClassHistItem_Ver1.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}

	private AffClassHistItem_ver1 toDomain(BsymtAffClassHistItem_Ver1 entity) {
		return AffClassHistItem_ver1.createFromJavaType(entity.sid, entity.historyId, entity.classificationCode);
	}

	@Override
	public void add(AffClassHistItem_ver1 item) {
		BsymtAffClassHistItem_Ver1 entity = new BsymtAffClassHistItem_Ver1(item.getHistoryId(), item.getEmployeeId(),
				item.getClassificationCode().v());
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(AffClassHistItem_ver1 item) {
		Optional<BsymtAffClassHistItem_Ver1> entityOpt = this.queryProxy().find(item.getHistoryId(),
				BsymtAffClassHistItem_Ver1.class);

		if (!entityOpt.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		BsymtAffClassHistItem_Ver1 ent = entityOpt.get();
		if (item.getClassificationCode() != null){
			ent.classificationCode = item.getClassificationCode().v();
		}
		this.commandProxy().update(ent);

	}

	@Override
	public void delete(String historyId) {
		Optional<BsymtAffClassHistItem_Ver1> entityOpt = this.queryProxy().find(historyId,
				BsymtAffClassHistItem_Ver1.class);

		if (!entityOpt.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		this.commandProxy().update(entityOpt.get());
	}
	
	@Override
	public List<AffClassHistItem_ver1> searchClassification(GeneralDate baseDate,
			List<String> classificationCodes) {

		if (CollectionUtil.isEmpty(classificationCodes)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<BsymtAffClassHistItem_Ver1> cq = criteriaBuilder
				.createQuery(BsymtAffClassHistItem_Ver1.class);

		// root data
		Root<BsymtAffClassHistItem_Ver1> root = cq.from(BsymtAffClassHistItem_Ver1.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// classification in data classification
		lstpredicateWhere.add(criteriaBuilder
				.and(root.get(BsymtAffClassHistItem_Ver1_.classificationCode)
						.in(classificationCodes)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(BsymtAffClassHistItem_Ver1_.bsymtAffClassHistory)
						.get(BsymtAffClassHistory_Ver1_.startDate), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(BsymtAffClassHistItem_Ver1_.bsymtAffClassHistory)
						.get(BsymtAffClassHistory_Ver1_.endDate), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<BsymtAffClassHistItem_Ver1> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffClassHistItem_ver1> searchClassification(List<String> employeeIds,
			GeneralDate baseDate, List<String> classificationCodes) {

		// check not data
		if (CollectionUtil.isEmpty(classificationCodes) || CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<BsymtAffClassHistItem_Ver1> cq = criteriaBuilder
				.createQuery(BsymtAffClassHistItem_Ver1.class);

		// root data
		Root<BsymtAffClassHistItem_Ver1> root = cq.from(BsymtAffClassHistItem_Ver1.class);

		// select root
		cq.select(root);
		
		List<BsymtAffClassHistItem_Ver1> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			CollectionUtil.split(classificationCodes, 1000, classSubList -> {
				// add where
				List<Predicate> lstpredicateWhere = new ArrayList<>();

				// employee id in data employee id
				lstpredicateWhere.add(criteriaBuilder
						.and(root.get(BsymtAffClassHistItem_Ver1_.sid).in(employeeIds)));

				// classification in data classification
				lstpredicateWhere.add(
						criteriaBuilder.and(root.get(BsymtAffClassHistItem_Ver1_.classificationCode)
								.in(classificationCodes)));

				// start date <= base date
				lstpredicateWhere
						.add(criteriaBuilder
								.lessThanOrEqualTo(
										root.get(BsymtAffClassHistItem_Ver1_.bsymtAffClassHistory)
												.get(BsymtAffClassHistory_Ver1_.startDate),
										baseDate));

				// endDate >= base date
				lstpredicateWhere
						.add(criteriaBuilder
								.greaterThanOrEqualTo(
										root.get(BsymtAffClassHistItem_Ver1_.bsymtAffClassHistory)
												.get(BsymtAffClassHistory_Ver1_.endDate),
										baseDate));

				// set where to SQL
				cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

				// create query
				TypedQuery<BsymtAffClassHistItem_Ver1> query = em.createQuery(cq);
				resultList.addAll(query.getResultList());
			});
		});
		
		// convert.
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
