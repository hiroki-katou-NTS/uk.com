/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

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

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfoPK;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfoPK_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo_;

/**
 * The Class JpaWorkplaceConfigInfoRepository.
 */
@Stateless
public class JpaWorkplaceConfigInfoRepository extends JpaRepository implements WorkplaceConfigInfoRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkplaceConfigInfo> find(String companyId, String historyId) {
		List<WorkplaceHierarchy> lstWorkplaceHierarchy = new ArrayList<>();
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWkpConfigInfo> cq = criteriaBuilder.createQuery(BsymtWkpConfigInfo.class);
		Root<BsymtWkpConfigInfo> root = cq.from(BsymtWkpConfigInfo.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWkpConfigInfo_.bsymtWkpConfigInfoPK).get(BsymtWkpConfigInfoPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWkpConfigInfo_.bsymtWkpConfigInfoPK).get(BsymtWkpConfigInfoPK_.historyId), historyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		lstWorkplaceHierarchy = em.createQuery(cq).getResultList().stream().map(item -> {
			return this.toDomain(item);
		}).collect(Collectors.toList());
		return Optional.of(new WorkplaceConfigInfo(new JpaWorkplaceConfigInfoGetMemento(companyId, historyId, lstWorkplaceHierarchy)));
	}

	private WorkplaceHierarchy toDomain(BsymtWkpConfigInfo item) {
		return new WorkplaceHierarchy(new JpaWorkplaceHierarchyGetMemento(item));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#add(nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo)
	 */
	@Override
	public void add(WorkplaceConfigInfo workplaceConfigInfo) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#addList(nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo)
	 */
	@Override
	public void addList(WorkplaceConfigInfo wkpConfigInfo) {
		List<BsymtWkpConfigInfo> lstEntity = this.convertToListEntity(wkpConfigInfo);
		this.commandProxy().insertAll(lstEntity);
	}

	/**
	 * Convert to list entity.
	 *
	 * @param wkpConfigInfo
	 *            the wkp config info
	 * @return the list
	 */
	private List<BsymtWkpConfigInfo> convertToListEntity(WorkplaceConfigInfo wkpConfigInfo) {
		return wkpConfigInfo.getWkpHierarchy().stream().map(item -> {
			BsymtWkpConfigInfo entity = new BsymtWkpConfigInfo();
			BsymtWkpConfigInfoPK pk = new BsymtWkpConfigInfoPK();
			pk.setCid(wkpConfigInfo.getCompanyId());
			pk.setHistoryId(wkpConfigInfo.getHistoryId().v());
			pk.setWkpid(item.getWorkplaceId().v());
			entity.setBsymtWkpConfigInfoPK(pk);
			entity.setHierarchyCd(item.getHierarchyCode().v());
			return entity;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<WorkplaceConfigInfo> findByStartDate(String companyId, GeneralDate startDate) {
		return null;
	}

}
