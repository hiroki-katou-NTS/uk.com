package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
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
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime_;

/**
 * The Class JpaDivergenceReasonInputMethodRepository.
 */
@Stateless
public class JpaDivergenceReasonInputMethodRepository extends JpaRepository
		implements DivergenceReasonInputMethodRepository {
	
	private final static String FIND_DVGC_TIME;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT d FROM KrcstDvgcTime d ");
		builderString.append("WHERE d.id.cid = :cid ");
		builderString.append("AND d.id.no IN :no ");
		builderString.append("AND d.dvgcReasonSelected = :dvgcReasonSelected");
		FIND_DVGC_TIME = builderString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#getAllDivTime(java.lang.String)
	 */
	@Override
	public List<DivergenceReasonInputMethod> getAllDivTime(String companyId) {

		return this.findByCompanyId(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#getDivTimeInfo(java.lang.String,
	 * int)
	 */
	@Override
	public Optional<DivergenceReasonInputMethod> getDivTimeInfo(String companyId, int divTimeNo) {

		KrcstDvgcTimePK PK = new KrcstDvgcTimePK(companyId, divTimeNo);
		return this.queryProxy().find(PK, KrcstDvgcTime.class).map(e->this.toDomain(e));
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the company divergence reference time history
	 */
	private DivergenceReasonInputMethod toDomain(KrcstDvgcTime entities) {
		DivergenceReasonInputMethodGetMemento memento = new JpaDivergenceReasonInputMethodGetMemento(entities);
		return new DivergenceReasonInputMethod(memento);
	}

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	private List<DivergenceReasonInputMethod> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcTime> KrcstDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcTime.isEmpty() ? new ArrayList<DivergenceReasonInputMethod>()
				: KrcstDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.DivergenceReasonInputMethod)
	 */
	@Override
	public void update(DivergenceReasonInputMethod domain) {
		this.commandProxy().update(this.toEntity(domain));

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc time
	 */
	private KrcstDvgcTime toEntity(DivergenceReasonInputMethod domain) {

		KrcstDvgcTimePK PK = new KrcstDvgcTimePK(domain.getCompanyId(), domain.getDivergenceTimeNo());
		KrcstDvgcTime entity = this.queryProxy().find(PK, KrcstDvgcTime.class).orElse(new KrcstDvgcTime());

		domain.saveToMemento(new JpaDivergenceReasonInputMethodSetMemento(entity));

		return entity;
	}

	@Override
	public List<DivergenceReasonInputMethod> getByCidAndLstTimeInfo(String companyId, List<Integer> divTimeNos, int useClassification) {
		return this.queryProxy().query(FIND_DVGC_TIME, KrcstDvgcTime.class)
				.setParameter("cid", companyId)
				.setParameter("no", divTimeNos)
				.setParameter("dvgcReasonSelected", BigDecimal.valueOf(useClassification))
				.getList(t -> toDomain(t));
	}

}
