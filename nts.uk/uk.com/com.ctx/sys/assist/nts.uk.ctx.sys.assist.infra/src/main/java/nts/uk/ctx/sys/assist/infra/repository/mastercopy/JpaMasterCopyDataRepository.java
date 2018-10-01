/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.*;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
public class JpaMasterCopyDataRepository extends JpaRepository implements MasterCopyDataRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyId(java.lang.String)
	 */
	@Override
	public MasterCopyData findByCategoryNo(Integer categoryNo) {
		Optional<SspmtMastercopyCategory> categoryEntity = this.queryProxy().find(categoryNo, SspmtMastercopyCategory.class);
		if(categoryEntity.isPresent()) {
			return this.toDomain(categoryEntity.get());
		}
		return null;	
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#doCopy(java.lang.String, nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod, java.lang.String)
	 */
	@Override
	public void doCopy(String tableName, List<String> keys, CopyMethod copyMethod, String companyId, boolean isOnlyCid) {
		//case 0,1
        DataCopyHandler.DataCopyHandlerBuilder.aDataCopyHandler()
                .withOnlyCid(isOnlyCid)
                .withCompanyId(companyId)
                .withCopyMethod(copyMethod)
                .withEntityManager(getEntityManager())
                .withKeys(keys)
                .withTableName(tableName)
                .buildQuery()
                .build()
                .doCopy();
	}

	@Override
	public MasterCopyCategory findCatByCategoryNo(Integer categoryNo) {
		MasterCopyCategory masterCopyCategory = new MasterCopyCategory();
		Optional<SspmtMastercopyCategory> categoryEntity = this.queryProxy().find(categoryNo, SspmtMastercopyCategory.class);
		if(categoryEntity.isPresent()) {
			SspmtMastercopyCategory categoryEnt = categoryEntity.get();
			masterCopyCategory.setCategoryName(new MasterCopyCategoryName(categoryEnt.getCategoryName()));
			masterCopyCategory.setCategoryNo(new MasterCopyCategoryNo(categoryNo));
			masterCopyCategory.setOrder(new MasterCopyCategoryOrder(categoryEnt.getCategoryOrder().intValue()));
			masterCopyCategory.setSystemType(SystemType.valueOf(categoryEnt.getSystemType().intValue()));
			return masterCopyCategory;
		}
		return null;
	}

	/**
	 * To domain.
	 *
	 * @param categoryEntity the category entity
	 * @return the master copy data
	 */
	private MasterCopyData toDomain(SspmtMastercopyCategory categoryEntity) {
		// query dataEntites
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyData> cq = criteriaBuilder.createQuery(SspmtMastercopyData.class);
		Root<SspmtMastercopyData> root = cq.from(SspmtMastercopyData.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(SspmtMastercopyData_.id).get(SspmtMastercopyDataPK_.categoryNo),
				categoryEntity.getCategoryNo()));
		cq.where(predicates.toArray(new Predicate[] {}));
		List<SspmtMastercopyData> dataEntities = em.createQuery(cq).getResultList();
		// get memento
		JpaMasterCopyDataGetMemento memento = new JpaMasterCopyDataGetMemento(categoryEntity, dataEntities);
		return new MasterCopyData(memento);
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
		if (categoryEntity.getCategoryNo() == null)
			categoryEntity.setCategoryNo(domain.getCategoryNo().v());
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
	public List<MasterCopyData> findByListCategoryNo(List<Integer> masterCopyIds) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyCategory> cq = criteriaBuilder.createQuery(SspmtMastercopyCategory.class);
		Root<SspmtMastercopyCategory> root = cq.from(SspmtMastercopyCategory.class);
		// Build query
		cq.select(root);
		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(root.get(SspmtMastercopyCategory_.categoryNo).in(masterCopyIds));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		// query data
		List<SspmtMastercopyCategory> sspmtMastercopyDatas = em.createQuery(cq).getResultList();
		return sspmtMastercopyDatas.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
	}

}
