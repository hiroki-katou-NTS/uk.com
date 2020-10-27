package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcmtDvgcEralMsgCom;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcmtDvgcEralMsgComPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcmtDvgcEralMsgComPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcmtDvgcEralMsgCom_;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaDivergenceTimeErrorAlarmMessageRepository.
 */
@Stateless
public class JpaDivTimeErrAlarmMsgRepo extends JpaRepository
		implements DivergenceTimeErrorAlarmMessageRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#getByDivergenceTimeNo(java.
	 * lang. Integer)
	 */
	@Override
	public Optional<DivergenceTimeErrorAlarmMessage> findByDivergenceTimeNo(CompanyId cId, Integer divergenceTimeNo) {
		KrcmtDvgcEralMsgComPK pk = new KrcmtDvgcEralMsgComPK(cId.v(), divergenceTimeNo);

		return this.queryProxy().find(pk, KrcmtDvgcEralMsgCom.class).map(item -> this.toDomain(item));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.message.DivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void add(DivergenceTimeErrorAlarmMessage message) {
		this.commandProxy().insert(this.toEntity(message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#update(nts.uk.ctx.at.record.
	 * dom. divergence.time.message.DivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void update(DivergenceTimeErrorAlarmMessage message) {
		this.commandProxy().update(this.toEntity(message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#findByDivergenceTimeNoList(nts.
	 * uk.ctx.at.shared.dom.common.CompanyId, java.util.List)
	 */
	@Override
	public List<DivergenceTimeErrorAlarmMessage> findByDivergenceTimeNoList(CompanyId cId,
			List<Integer> divergenceTimeNoList) {
		
		if(CollectionUtil.isEmpty(divergenceTimeNoList)) {
			return Collections.emptyList();
		}
		
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcEralMsgCom> cq = criteriaBuilder.createQuery(KrcmtDvgcEralMsgCom.class);
		Root<KrcmtDvgcEralMsgCom> root = cq.from(KrcmtDvgcEralMsgCom.class);

		// Build query
		cq.select(root);

		List<KrcmtDvgcEralMsgCom> krcmtDvgcEralMsgCom = new ArrayList<>();
		CollectionUtil.split(divergenceTimeNoList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcEralMsgCom_.id).get(KrcmtDvgcEralMsgComPK_.cid), cId));

			// dvgcTimeNo in divergenceTimeNoList
			predicates.add(
					root.get(KrcmtDvgcEralMsgCom_.id).get(KrcmtDvgcEralMsgComPK_.dvgcTimeNo).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));

			// query data
			krcmtDvgcEralMsgCom.addAll(em.createQuery(cq).getResultList());
		});

		// return
		return krcmtDvgcEralMsgCom.isEmpty() ? new ArrayList<DivergenceTimeErrorAlarmMessage>()
				: krcmtDvgcEralMsgCom.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence time error alarm message
	 */
	private DivergenceTimeErrorAlarmMessage toDomain(KrcmtDvgcEralMsgCom entity) {
		DivergenceTimeErrorAlarmMessageGetMemento memento = new JpaDivergenceTimeErrorAlarmMessageGetMemento(entity);
		return new DivergenceTimeErrorAlarmMessage(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc time ea msg
	 */
	private KrcmtDvgcEralMsgCom toEntity(DivergenceTimeErrorAlarmMessage domain) {
		KrcmtDvgcEralMsgComPK pk = new KrcmtDvgcEralMsgComPK(domain.getCId().v(), domain.getDivergenceTimeNo());

		KrcmtDvgcEralMsgCom entity = this.queryProxy().find(pk, KrcmtDvgcEralMsgCom.class)
				.orElse(new KrcmtDvgcEralMsgCom());

		DivergenceTimeErrorAlarmMessageSetMemento memento = new JpaDivergenceTimeErrorAlarmMessageSetMemento(entity);
		domain.saveToMemento(memento);

		return entity;
	}

}
