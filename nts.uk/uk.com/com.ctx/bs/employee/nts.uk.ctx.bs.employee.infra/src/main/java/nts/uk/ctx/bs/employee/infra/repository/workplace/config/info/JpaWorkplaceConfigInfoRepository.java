/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
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

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.
     * WorkplaceConfigInfoRepository#addList(nts.uk.ctx.bs.employee.dom.
     * workplace.configinfo.WorkplaceConfigInfo)
     */
    @Override
    public void add(WorkplaceConfigInfo wkpConfigInfo) {
        this.commandProxy().insertAll(this.toListEntity(wkpConfigInfo));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.
     * WorkplaceConfigInfoRepository#update(nts.uk.ctx.bs.employee.dom.workplace
     * .config.info.WorkplaceConfigInfo)
     */
    @Override
    public void update(WorkplaceConfigInfo wkpConfigInfo) {
        this.commandProxy().updateAll(this.toListEntity(wkpConfigInfo));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.
     * WorkplaceConfigInfoRepository#removeWkpHierarchy(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void removeWkpHierarchy(String companyId, String historyId, String wkpId) {
        this.commandProxy().remove(BsymtWkpConfigInfo.class, new BsymtWkpConfigInfoPK(companyId, historyId, wkpId));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.
     * WorkplaceConfigInfoRepository#find(java.lang.String, java.lang.String)
     */
    @Override
    public Optional<WorkplaceConfigInfo> find(String companyId, String historyId) {
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
        lstpredicateWhere.add(criteriaBuilder.equal(
                root.get(BsymtWkpConfigInfo_.bsymtWkpConfigInfoPK).get(BsymtWkpConfigInfoPK_.historyId), historyId));

        cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
        cq.orderBy(criteriaBuilder.asc(root.get(BsymtWkpConfigInfo_.hierarchyCd)));

        List<BsymtWkpConfigInfo> lstEntity = em.createQuery(cq).getResultList();
        
        if (lstEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new WorkplaceConfigInfo(
                new JpaWorkplaceConfigInfoGetMemento(lstEntity)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.
     * WorkplaceConfigInfoRepository#findByWkpId(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Optional<WorkplaceConfigInfo> find(String companyId, String historyId, String wkpId) {
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
        lstpredicateWhere.add(criteriaBuilder.equal(
                root.get(BsymtWkpConfigInfo_.bsymtWkpConfigInfoPK).get(BsymtWkpConfigInfoPK_.historyId), historyId));
        lstpredicateWhere.add(criteriaBuilder
                .equal(root.get(BsymtWkpConfigInfo_.bsymtWkpConfigInfoPK).get(BsymtWkpConfigInfoPK_.wkpid), wkpId));

        cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

        List<BsymtWkpConfigInfo> lstEntity = em.createQuery(cq).getResultList();
        
        if (lstEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new WorkplaceConfigInfo(
                new JpaWorkplaceConfigInfoGetMemento(lstEntity)));
    }

    /**
     * To list entity.
     *
     * @param wkpConfigInfo the wkp config info
     * @return the list
     */
    private List<BsymtWkpConfigInfo> toListEntity(WorkplaceConfigInfo wkpConfigInfo) {
        String companyId = wkpConfigInfo.getCompanyId();
        String historyId = wkpConfigInfo.getHistoryId();

        List<BsymtWkpConfigInfo> lstEntity = new ArrayList<>();

        for (WorkplaceHierarchy wkpHierarchy : wkpConfigInfo.getLstWkpHierarchy()) {
            BsymtWkpConfigInfoPK pk = new BsymtWkpConfigInfoPK(companyId, historyId, wkpHierarchy.getWorkplaceId());
            Optional<BsymtWkpConfigInfo> optional = this.queryProxy().find(pk, BsymtWkpConfigInfo.class);

            BsymtWkpConfigInfo entity = null;
            if (optional.isPresent()) {
                entity = optional.get();
            } else {
                entity = new BsymtWkpConfigInfo();
                entity.setBsymtWkpConfigInfoPK(pk);
            }
            lstEntity.add(entity);
        }
        JpaWorkplaceConfigInfoSetMemento memento = new JpaWorkplaceConfigInfoSetMemento(lstEntity);
        wkpConfigInfo.saveToMemento(memento);

        return lstEntity;
    }

}
