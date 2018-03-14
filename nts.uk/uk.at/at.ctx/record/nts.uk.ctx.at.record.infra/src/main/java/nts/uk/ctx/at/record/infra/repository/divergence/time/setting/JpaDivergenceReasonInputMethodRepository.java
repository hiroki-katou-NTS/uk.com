package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrt;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrtPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime_;
import nts.uk.ctx.at.record.infra.repository.divergence.time.JpaCompanyDivergenceReferenceTimeSetMemento;

public class JpaDivergenceReasonInputMethodRepository extends JpaRepository implements DivergenceReasonInputMethodRepository{

	@Override
	public List<DivergenceReasonInputMethod> getAllDivTime(String companyId) {
		
		return this.findByCompanyId(companyId);
	}
	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the company divergence reference time history
	 */
	private DivergenceReasonInputMethod toDomain(KrcstDvgcTime entities) {
		DivergenceReasonInputMethodGetMemento memento = new JpaDivergenceReasonInputMethodRepositoryGetMemento(entities);
		return new DivergenceReasonInputMethod(memento);
	}
	
	private List<DivergenceReasonInputMethod> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get( KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcTime> KrcstDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcTime.isEmpty() ? new ArrayList<DivergenceReasonInputMethod>()
				: KrcstDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
}
