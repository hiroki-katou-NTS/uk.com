package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

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

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsg;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsgPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsgPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsg_;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkTypeDivergenceTimeErrorAlarmMessageRepository.
 */
@Stateless
public class JpaWorkTypeDivTimeErrAlarmMsgRepo extends JpaRepository
		implements WorkTypeDivergenceTimeErrorAlarmMessageRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * WorkTypeDivergenceTimeErrorAlarmMessageRepository#getByDivergenceTimeNo(
	 * java. lang.Integer)
	 */
	@Override
	public Optional<WorkTypeDivergenceTimeErrorAlarmMessage> getByDivergenceTimeNo(Integer divergenceTimeNo,
			CompanyId cId, BusinessTypeCode workTypeCode) {
		KrcstDvgcwtTimeEaMsgPK pk = new KrcstDvgcwtTimeEaMsgPK(cId.v(), divergenceTimeNo, workTypeCode.v());

		return this.queryProxy().find(pk, KrcstDvgcwtTimeEaMsg.class).map(item -> this.toDomain(item));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * WorkTypeDivergenceTimeErrorAlarmMessageRepository#add(nts.uk.ctx.at.
	 * record.
	 * dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void add(WorkTypeDivergenceTimeErrorAlarmMessage message) {
		this.commandProxy().insert(this.toEntity(message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * WorkTypeDivergenceTimeErrorAlarmMessageRepository#update(nts.uk.ctx.at.
	 * record
	 * .dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void update(WorkTypeDivergenceTimeErrorAlarmMessage message) {
		this.commandProxy().update(this.toEntity(message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * WorkTypeDivergenceTimeErrorAlarmMessageRepository#
	 * getByDivergenceTimeNoList(java.util.List,
	 * nts.uk.ctx.at.shared.dom.common.CompanyId,
	 * nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.
	 * BusinessTypeCode)
	 */
	@Override
	public List<WorkTypeDivergenceTimeErrorAlarmMessage> getByDivergenceTimeNoList(List<Integer> divergenceTimeNoList,
			CompanyId cId, BusinessTypeCode workTypeCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcwtTimeEaMsg> cq = criteriaBuilder.createQuery(KrcstDvgcwtTimeEaMsg.class);
		Root<KrcstDvgcwtTimeEaMsg> root = cq.from(KrcstDvgcwtTimeEaMsg.class);

		// Build query
		cq.select(root);

		List<KrcstDvgcwtTimeEaMsg> resultList = new ArrayList<>();

		CollectionUtil.split(divergenceTimeNoList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcwtTimeEaMsg_.id).get(KrcstDvgcwtTimeEaMsgPK_.cid), cId));
			predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcwtTimeEaMsg_.id).get(KrcstDvgcwtTimeEaMsgPK_.worktypeCd),
					workTypeCode));
			// dvgcTimeNo in divergenceTimeNoList
			predicates.add(
					root.get(KrcstDvgcwtTimeEaMsg_.id).get(KrcstDvgcwtTimeEaMsgPK_.dvgcTimeNo).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));

			// query data
			resultList.addAll(em.createQuery(cq).getResultList());
		});

		// return
		return resultList.isEmpty() ? new ArrayList<WorkTypeDivergenceTimeErrorAlarmMessage>()
				: resultList.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence time error alarm message
	 */
	private WorkTypeDivergenceTimeErrorAlarmMessage toDomain(KrcstDvgcwtTimeEaMsg entity) {
		WorkTypeDivergenceTimeErrorAlarmMessageGetMemento memento = new JpaWorkTypeDivergenceTimeErrorAlarmMessageGetMemento(
				entity);
		return new WorkTypeDivergenceTimeErrorAlarmMessage(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc time ea msg
	 */
	private KrcstDvgcwtTimeEaMsg toEntity(WorkTypeDivergenceTimeErrorAlarmMessage domain) {
		KrcstDvgcwtTimeEaMsgPK pk = new KrcstDvgcwtTimeEaMsgPK(domain.getCId().v(), domain.getDivergenceTimeNo(),
				domain.getWorkTypeCode().v());

		KrcstDvgcwtTimeEaMsg entity = this.queryProxy().find(pk, KrcstDvgcwtTimeEaMsg.class)
				.orElse(new KrcstDvgcwtTimeEaMsg());

		WorkTypeDivergenceTimeErrorAlarmMessageSetMemento memento = new JpaWorkTypeDivergenceTimeErrorAlarmMessageSetMemento(
				entity);
		domain.saveToMemento(memento);

		return entity;
	}

}
