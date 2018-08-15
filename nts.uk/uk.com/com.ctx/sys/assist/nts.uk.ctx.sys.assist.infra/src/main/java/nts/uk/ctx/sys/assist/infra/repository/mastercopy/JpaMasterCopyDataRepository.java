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
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyDataPK_;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData_;
import nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler.KmfmtRetentionYearlyDataCopyHandler;

@Stateless
public class JpaMasterCopyDataRepository extends JpaRepository implements MasterCopyDataRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyId(java.lang.String)
	 */
	@Override
	public List<MasterCopyData> findByMasterCopyId(Integer categoryNo) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(SspmtMastercopyData_.id).get(SspmtMastercopyDataPK_.categoryNo), categoryNo));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// query data
		List<SspmtMastercopyData> sspmtMastercopyDatas = em.createQuery(cq).getResultList();

		return sspmtMastercopyDatas.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
	
	@Override
	public void doCopy(String tableName, CopyMethod copyMethod, String companyId) {
		new KmfmtRetentionYearlyDataCopyHandler(this.getEntityManager(), copyMethod, companyId).doCopy();
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the master copy data
	 */
	private MasterCopyData toDomain(SspmtMastercopyData entity) {
		// MasterCopyDataGetMemento memento = new JpaMasterCopyDataGetMemento(entity);
		// return new MasterCopyData(memento);
		
		return null;// viet lai ham toDomain vi gio domain da thay doi
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the sspmt mastercopy data
	 */
	private List<SspmtMastercopyData> toDataEntites(MasterCopyData domain) {
		// query data entites
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(SspmtMastercopyData_.id).get(SspmtMastercopyDataPK_.categoryNo),
				domain.getCategoryNo()));
		cq.where(predicates.toArray(new Predicate[] {}));
		List<SspmtMastercopyData> listDataEntites = em.createQuery(cq).getResultList();
		// category entity
		SspmtMastercopyCategory categoryEntity = this.queryProxy()
				.find(domain.getCategoryNo(), SspmtMastercopyCategory.class).orElse(new SspmtMastercopyCategory());
		// set memento
		if(categoryEntity.getCategoryNo()==null) categoryEntity.setCategoryNo(domain.getCategoryNo().v());
		JpaMasterCopyDataSetMemento memento = new JpaMasterCopyDataSetMemento(categoryEntity, listDataEntites);
		domain.saveToMemento(memento);
		return listDataEntites;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyIds(java.lang.String)
	 */
	@Override
	public List<MasterCopyData> findByMasterCopyIds(List<Integer> masterCopyIds) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(root.get(SspmtMastercopyData_.id).get(SspmtMastercopyDataPK_.categoryNo).in(masterCopyIds));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// query data
		List<SspmtMastercopyData> sspmtMastercopyDatas = em.createQuery(cq).getResultList();

		return sspmtMastercopyDatas.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

}
