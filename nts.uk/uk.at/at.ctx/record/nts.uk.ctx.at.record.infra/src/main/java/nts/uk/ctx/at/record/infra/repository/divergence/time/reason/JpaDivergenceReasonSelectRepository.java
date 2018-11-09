package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReasonPK;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReasonPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaDivergenceReasonSelectRepository.
 */
@Stateless
public class JpaDivergenceReasonSelectRepository extends JpaRepository implements DivergenceReasonSelectRepository {
	
	private static final String SELECT_CODE_NO_AND_NAME = "SELECT c.id.reasonCd, c.id.no, c.reason FROM KrcstDvgcReason c"
			+ " WHERE c.id.cid = :companyID AND c.id.reasonCd IN :lstReasonCode";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectRepository#findAllReason(int, java.lang.String)
	 */
	@Override
	public List<DivergenceReasonSelect> findAllReason(int divTimeNo, String companyId) {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcReason> cq = criteriaBuilder.createQuery(KrcstDvgcReason.class);
		Root<KrcstDvgcReason> root = cq.from(KrcstDvgcReason.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcReason_.id).get(KrcstDvgcReasonPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcReason_.id).get(KrcstDvgcReasonPK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcReason> KrcstDvgcReason = em.createQuery(cq).getResultList();

		// return
		if (KrcstDvgcReason != null)
			return KrcstDvgcReason.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
		return new ArrayList<DivergenceReasonSelect>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectRepository#findReasonInfo(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<DivergenceReasonSelect> findReasonInfo(int divTimeNo, String companyId, String reasonCode) {

		// Get Primary Key
		KrcstDvgcReasonPK PK = new KrcstDvgcReasonPK(divTimeNo, companyId, reasonCode);

		return this.queryProxy().find(PK, KrcstDvgcReason.class).map(e -> toDomain(e));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectRepository#update(int,
	 * nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect)
	 */
	@Override
	public void update(int divTimeNo, DivergenceReasonSelect divergenceReasonSelect) {
		this.commandProxy().update(this.toEntity(divTimeNo, divergenceReasonSelect));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectRepository#delete(java.lang.Integer,
	 * nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect)
	 */
	@Override
	public void delete(Integer divTimeNo, DivergenceReasonSelect divReasonSelect) {

		// Get Primary Key
		KrcstDvgcReasonPK PK = new KrcstDvgcReasonPK(divTimeNo, AppContexts.user().companyId(),
				divReasonSelect.getDivergenceReasonCode().toString());

		// Find Entity
		Optional<KrcstDvgcReason> reason = this.queryProxy().find(PK, KrcstDvgcReason.class);

		if (reason.isPresent()) {
			// if present
			this.commandProxy().remove(reason.get());
			this.getEntityManager().flush();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectRepository#add(java.lang.Integer,
	 * nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect)
	 */
	@Override
	public void add(Integer divTimeNo, DivergenceReasonSelect divReasonSelect) {

		this.commandProxy().insert(toEntity(divTimeNo, divReasonSelect));

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc reason
	 */
	private KrcstDvgcReason toEntity(int divTimeNo, DivergenceReasonSelect domain) {

		KrcstDvgcReason entity = new KrcstDvgcReason();

		// convert domain to Entity
		domain.saveToMemento(new JpaDivergenceReasonSelectSetMemento(entity));

		// Set DivergenceTimeNo
		entity.getId().setNo(divTimeNo);

		// Set ConpanyID
		entity.getId().setCid(AppContexts.user().companyId());

		// Return
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence reason select
	 */
	private DivergenceReasonSelect toDomain(KrcstDvgcReason entity) {

		// convert Enyity to domain
		DivergenceReasonSelectGetMemento memento = new JpaDivergenceReasonSelectGetMemento(entity);

		return new DivergenceReasonSelect(memento);

	}

	@Override
	public Map<Pair<String, String>, String> getNameByCodeNo(String companyId, List<String> lstReasonCode) {
		return this.queryProxy()
				.query(SELECT_CODE_NO_AND_NAME, Object[].class)
				.setParameter("companyID", companyId)
				.setParameter("lstReasonCode", lstReasonCode)
				.getList().stream().collect(Collectors.toMap(s -> Pair.of(String.valueOf(s[0]), String.valueOf(s[1])), s-> String.valueOf(s[2])));
	}

}
