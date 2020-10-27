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
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReason;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReasonPK;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReasonPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReason_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaDivergenceReasonSelectRepository.
 */
@Stateless
public class JpaDivergenceReasonSelectRepository extends JpaRepository implements DivergenceReasonSelectRepository {
	
	private static final String SELECT_CODE_NO_AND_NAME = "SELECT c.id.reasonCd, c.id.no, c.reason FROM KrcmtDvgcReason c"
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
		CriteriaQuery<KrcmtDvgcReason> cq = criteriaBuilder.createQuery(KrcmtDvgcReason.class);
		Root<KrcmtDvgcReason> root = cq.from(KrcmtDvgcReason.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcReason_.id).get(KrcmtDvgcReasonPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcReason_.id).get(KrcmtDvgcReasonPK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcmtDvgcReason> KrcmtDvgcReason = em.createQuery(cq).getResultList();

		// return
		if (KrcmtDvgcReason != null)
			return KrcmtDvgcReason.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
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
		KrcmtDvgcReasonPK PK = new KrcmtDvgcReasonPK(divTimeNo, companyId, reasonCode);

		return this.queryProxy().find(PK, KrcmtDvgcReason.class).map(e -> toDomain(e));

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
		KrcmtDvgcReasonPK PK = new KrcmtDvgcReasonPK(divTimeNo, AppContexts.user().companyId(),
				divReasonSelect.getDivergenceReasonCode().toString());

		// Find Entity
		Optional<KrcmtDvgcReason> reason = this.queryProxy().find(PK, KrcmtDvgcReason.class);

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
	private KrcmtDvgcReason toEntity(int divTimeNo, DivergenceReasonSelect domain) {

		KrcmtDvgcReason entity = new KrcmtDvgcReason();

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
	private DivergenceReasonSelect toDomain(KrcmtDvgcReason entity) {

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
