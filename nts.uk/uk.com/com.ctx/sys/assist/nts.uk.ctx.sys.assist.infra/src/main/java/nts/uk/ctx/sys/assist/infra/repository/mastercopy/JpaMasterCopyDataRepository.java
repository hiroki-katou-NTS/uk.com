/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyDataPK_;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData_;

@Stateless
public class JpaMasterCopyDataRepository extends JpaRepository implements MasterCopyDataRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyId(java.lang.String)
	 */
	@Override
	public List<MasterCopyData> findByMasterCopyId(String masterCopyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder
				.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(SspmtMastercopyData_.sspmtMastercopyDataPK)
						.get(SspmtMastercopyDataPK_.masterCopyId), masterCopyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// query data
		List<SspmtMastercopyData> sspmtMastercopyDatas = em.createQuery(cq).getResultList();

		return sspmtMastercopyDatas.stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the master copy data
	 */
	private MasterCopyData toDomain(SspmtMastercopyData entity) {
		MasterCopyDataGetMemento memento = new JpaMasterCopyDataGetMemento(entity);
		return new MasterCopyData(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the sspmt mastercopy data
	 */
	private SspmtMastercopyData toEntity(MasterCopyData domain) {
		SspmtMastercopyData entity = this.queryProxy()
				.find(domain.getMasterCopyId(), SspmtMastercopyData.class)
				.orElse(new SspmtMastercopyData());

		MasterCopyDataSetMemento memento = new JpaMasterCopyDataSetMemento(entity);
		domain.saveToMemento(memento);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyIds(java.lang.String)
	 */
	@Override
	public List<MasterCopyData> findByMasterCopyIds(List<String> masterCopyIds) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder
				.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(root.get(SspmtMastercopyData_.sspmtMastercopyDataPK)
				.get(SspmtMastercopyDataPK_.masterCopyId).in(masterCopyIds));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// query data
		List<SspmtMastercopyData> sspmtMastercopyDatas = em.createQuery(cq).getResultList();

		return sspmtMastercopyDatas.stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

}
