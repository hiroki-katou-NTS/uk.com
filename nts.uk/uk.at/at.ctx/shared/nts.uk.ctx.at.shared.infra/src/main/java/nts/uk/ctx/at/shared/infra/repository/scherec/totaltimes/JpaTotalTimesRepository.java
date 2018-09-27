/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

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

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimes;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimesPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimesPK_;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimes_;

/**
 * The Class JpaTotalTimesRepository.
 */
@Stateless
public class JpaTotalTimesRepository extends JpaRepository implements TotalTimesRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository#
	 * getAllTotalTimes(java.lang.String)
	 */
	@Override
	public List<TotalTimes> getAllTotalTimes(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<KshstTotalTimes> query = builder.createQuery(KshstTotalTimes.class);
		Root<KshstTotalTimes> root = query.from(KshstTotalTimes.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(
				builder.equal(root.get(KshstTotalTimes_.kshstTotalTimesPK).get(KshstTotalTimesPK_.cid), companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		// order by closure id asc
		query.orderBy(builder.asc(root.get(KshstTotalTimes_.kshstTotalTimesPK).get(KshstTotalTimesPK_.totalTimesNo)));

		List<KshstTotalTimes> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		return result.stream().map(entity -> new TotalTimes(new JpaTotalTimesGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository#
	 * getTotalTimesDetail(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<TotalTimes> getTotalTimesDetail(String companyId, Integer totalCountNo) {
		KshstTotalTimesPK kshstTotalTimesPK = new KshstTotalTimesPK(companyId, totalCountNo);

		Optional<KshstTotalTimes> optKshstTotalTimes = this.queryProxy().find(kshstTotalTimesPK, KshstTotalTimes.class);

		if (!optKshstTotalTimes.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new TotalTimes(new JpaTotalTimesGetMemento(optKshstTotalTimes.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository#update(
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes)
	 */
	@Override
	public void update(TotalTimes totalTimes) {
		Optional<KshstTotalTimes> optional = this.queryProxy().find(
				new KshstTotalTimesPK(totalTimes.getCompanyId().v(), totalTimes.getTotalCountNo()),
				KshstTotalTimes.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Total times not existed.");
		}

		KshstTotalTimes entity = optional.get();
		totalTimes.saveToMemento(new JpaTotalTimesSetMemento(entity));
		this.commandProxy().update(entity);
	}

	private static final String FIND_ALL_BY_LIST_FRAME_NO = "SELECT a FROM KshstTotalTimes a "
			+ " WHERE a.kshstTotalTimesPK.cid = :companyId"
			+ " AND a.kshstTotalTimesPK.totalTimesNo IN :totalCountNos ";
	
	@Override
	public List<TotalTimes> getTotalTimesDetailByListNo(String companyId, List<Integer> totalCountNos) {
		if(totalCountNos.isEmpty())
			return Collections.emptyList();
		return this.queryProxy().query(FIND_ALL_BY_LIST_FRAME_NO, KshstTotalTimes.class)
				.setParameter("companyId", companyId)
				.setParameter("totalCountNos", totalCountNos)
				.getList(x -> new TotalTimes(new JpaTotalTimesGetMemento(x)));
	}

}
