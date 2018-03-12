package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrt;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrtPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrtPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrt_;

/**
 * The Class JpaCompanyDivergenceReferenceTimeRepository.
 */
@Stateless
public class JpaCompanyDivergenceReferenceTimeRepository extends JpaRepository
		implements CompanyDivergenceReferenceTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public Optional<CompanyDivergenceReferenceTime> findByKey(String histId, DivergenceType divergenceTimeNo) {
		KrcstDrtPK pk = new KrcstDrtPK();
		pk.setHistId(histId);
		pk.setDvgcTimeNo(divergenceTimeNo.value);

		KrcstDrt drt = this.queryProxy().find(pk, KrcstDrt.class).orElse(new KrcstDrt());

		return Optional.of(this.toDomain(drt));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<CompanyDivergenceReferenceTime> findAll(String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDrt> cq = criteriaBuilder.createQuery(KrcstDrt.class);
		Root<KrcstDrt> root = cq.from(KrcstDrt.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDrt_.id).get(KrcstDrtPK_.histId), histId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDrt> krcstDrts = em.createQuery(cq).getResultList();

		// return
		return krcstDrts.isEmpty() ? new ArrayList<CompanyDivergenceReferenceTime>()
				: krcstDrts.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void add(CompanyDivergenceReferenceTime domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void update(CompanyDivergenceReferenceTime domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#delete(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void delete(CompanyDivergenceReferenceTime domain) {
		this.commandProxy().remove(this.toEntity(domain));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company divergence reference time
	 */
	private CompanyDivergenceReferenceTime toDomain(KrcstDrt entity) {
		JpaCompanyDivergenceReferenceTimeGetMemento memento = new JpaCompanyDivergenceReferenceTimeGetMemento(entity);
		return new CompanyDivergenceReferenceTime(memento);
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcst drt
	 */
	private KrcstDrt toEntity(CompanyDivergenceReferenceTime domain) {
		KrcstDrtPK pk = new KrcstDrtPK();
		pk.setHistId(domain.getHistoryId());
		pk.setDvgcTimeNo(domain.getDivergenceTimeNo());

		KrcstDrt entity = this.queryProxy().find(pk, KrcstDrt.class).orElse(new KrcstDrt());
		domain.saveToMemento(new JpaCompanyDivergenceReferenceTimeSetMemento(entity));
		
		return entity;
	}
}
