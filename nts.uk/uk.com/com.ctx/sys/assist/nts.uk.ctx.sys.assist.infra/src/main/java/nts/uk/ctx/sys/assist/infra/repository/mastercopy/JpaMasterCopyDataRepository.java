/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

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
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryName;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryOrder;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.SystemType;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyCategory;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyCategory_;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyData;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyDataPK_;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyData_;


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
		Optional<SspctMastercopyCategory> categoryEntity = this.queryProxy().find(categoryNo, SspctMastercopyCategory.class);
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
		Optional<SspctMastercopyCategory> categoryEntity = this.queryProxy().find(categoryNo, SspctMastercopyCategory.class);
		if(categoryEntity.isPresent()) {
			SspctMastercopyCategory categoryEnt = categoryEntity.get();
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
	private MasterCopyData toDomain(SspctMastercopyCategory categoryEntity) {
		// query dataEntites
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspctMastercopyData> cq = criteriaBuilder.createQuery(SspctMastercopyData.class);
		Root<SspctMastercopyData> root = cq.from(SspctMastercopyData.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(SspctMastercopyData_.id).get(SspctMastercopyDataPK_.categoryNo),
				categoryEntity.getCategoryNo()));
		cq.where(predicates.toArray(new Predicate[] {}));
		List<SspctMastercopyData> dataEntities = em.createQuery(cq).getResultList();
		// get memento
		JpaMasterCopyDataGetMemento memento = new JpaMasterCopyDataGetMemento(categoryEntity, dataEntities);
		return new MasterCopyData(memento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#
	 * findByMasterCopyIds(java.lang.String)
	 */
	@Override
	public List<MasterCopyData> findByListCategoryNo(List<Integer> masterCopyIds) {
		
		if(CollectionUtil.isEmpty(masterCopyIds)) {
			return Collections.emptyList();
		}
		
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspctMastercopyCategory> cq = criteriaBuilder.createQuery(SspctMastercopyCategory.class);
		Root<SspctMastercopyCategory> root = cq.from(SspctMastercopyCategory.class);
		
		// Build query
		cq.select(root);
		
		List<SspctMastercopyCategory> resultList = new ArrayList<>();

		CollectionUtil.split(masterCopyIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// Add where conditions
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			lstpredicateWhere
					.add(root.get(SspctMastercopyCategory_.categoryNo).in(splitData));

			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			resultList.addAll(em.createQuery(cq).getResultList());
		});
		
		return resultList.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
	}

}
