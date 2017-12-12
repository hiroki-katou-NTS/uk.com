package nts.uk.ctx.at.shared.infra.repository.workingcondition;

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
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;


/**
 * The Class JpaWorkingConditionRepository.
 */
@Stateless
public class JpaWorkingConditionRepository extends JpaRepository implements WorkingConditionRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#findWokingCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> findWokingCondition(String employeeId, String histId) {
		return this.queryProxy()
				.find(new KshmtWorkingCondPK(employeeId, histId), KshmtWorkingCond.class)
				.map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#add(nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	public void add(WorkingCondition workingCondition) {
		this.commandProxy().insert(this.toEntity(workingCondition));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#update(nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	public void update(WorkingCondition workingCondition) {
		this.commandProxy().update(this.toEntity(workingCondition));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String employeeId, String histId) {
		this.commandProxy().remove(
				KshmtWorkingCond.class, 
				new KshmtWorkingCondPK(employeeId, histId));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#getAllWokingCondition()
	 */
	@Override
	public List<WorkingCondition> getAllWokingCondition(String companyId) {
		// get entity manager
			EntityManager em = this.getEntityManager();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder
				.createQuery(KshmtWorkingCond.class);
			
			// root data
			Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

			// select root
			cq.select(root);

			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// eq company id
			lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), companyId));
			
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// creat query
			TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

			// exclude select
			return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt working cond
	 */
	private KshmtWorkingCond toEntity(WorkingCondition domain) {
		KshmtWorkingCond entity = new KshmtWorkingCond();
		domain.saveToMemento(new JpaWorkingConditionSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the working condition
	 */
	private WorkingCondition toDomain(KshmtWorkingCond entity){
		return new WorkingCondition(new JpaWorkingConditionGetMemento(entity));
	}

}
