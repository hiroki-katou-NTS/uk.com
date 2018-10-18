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
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcTimeEaMsg;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcTimeEaMsgPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcTimeEaMsgPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcTimeEaMsg_;
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
		KrcstDvgcTimeEaMsgPK pk = new KrcstDvgcTimeEaMsgPK(cId.v(), divergenceTimeNo);

		return this.queryProxy().find(pk, KrcstDvgcTimeEaMsg.class).map(item -> this.toDomain(item));
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
		CriteriaQuery<KrcstDvgcTimeEaMsg> cq = criteriaBuilder.createQuery(KrcstDvgcTimeEaMsg.class);
		Root<KrcstDvgcTimeEaMsg> root = cq.from(KrcstDvgcTimeEaMsg.class);

		// Build query
		cq.select(root);

		List<KrcstDvgcTimeEaMsg> krcstDvgcTimeEaMsg = new ArrayList<>();
		CollectionUtil.split(divergenceTimeNoList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTimeEaMsg_.id).get(KrcstDvgcTimeEaMsgPK_.cid), cId));

			// dvgcTimeNo in divergenceTimeNoList
			predicates.add(
					root.get(KrcstDvgcTimeEaMsg_.id).get(KrcstDvgcTimeEaMsgPK_.dvgcTimeNo).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));

			// query data
			krcstDvgcTimeEaMsg.addAll(em.createQuery(cq).getResultList());
		});

		// return
		return krcstDvgcTimeEaMsg.isEmpty() ? new ArrayList<DivergenceTimeErrorAlarmMessage>()
				: krcstDvgcTimeEaMsg.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence time error alarm message
	 */
	private DivergenceTimeErrorAlarmMessage toDomain(KrcstDvgcTimeEaMsg entity) {
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
	private KrcstDvgcTimeEaMsg toEntity(DivergenceTimeErrorAlarmMessage domain) {
		KrcstDvgcTimeEaMsgPK pk = new KrcstDvgcTimeEaMsgPK(domain.getCId().v(), domain.getDivergenceTimeNo());

		KrcstDvgcTimeEaMsg entity = this.queryProxy().find(pk, KrcstDvgcTimeEaMsg.class)
				.orElse(new KrcstDvgcTimeEaMsg());

		DivergenceTimeErrorAlarmMessageSetMemento memento = new JpaDivergenceTimeErrorAlarmMessageSetMemento(entity);
		domain.saveToMemento(memento);

		return entity;
	}

}
