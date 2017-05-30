/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveCom_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtNormalVacationSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtNormalVacationSet_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet_;

/**
 * The Class JpaCompensLeaveComSetRepository.
 */
@Stateless
public class JpaCompensLeaveComSetRepository extends JpaRepository implements CompensLeaveComSetRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveComSetting)
     */
    @Override
    public void insert(CompensatoryLeaveComSetting setting) {
        this.commandProxy().insert(toEntity(setting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#update(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveComSetting)
     */
    @Override
    public void update(CompensatoryLeaveComSetting setting) {
        this.commandProxy().update(toEntity(setting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#find(java.lang.String)
     */
    @Override
    public CompensatoryLeaveComSetting find(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtCompensLeaveCom> query = builder.createQuery(KmfmtCompensLeaveCom.class);
        Root<KmfmtCompensLeaveCom> root = query.from(KmfmtCompensLeaveCom.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtCompensLeaveCom_.cid), companyId));

        List<KmfmtCompensLeaveCom> result = em.createQuery(query).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        KmfmtCompensLeaveCom entity = result.get(0);
        KmfmtNormalVacationSet entityNormal = findNormal(companyId);
        KmfmtOccurVacationSet entityOccur = findOccurVacation(companyId);
        
        return new CompensatoryLeaveComSetting(
                new JpaCompensatoryLeaveComGetMemento(entity, entityNormal, entityOccur));
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt compens leave com
     */
    private KmfmtCompensLeaveCom toEntity(CompensatoryLeaveComSetting setting) {
        KmfmtCompensLeaveCom entity = new KmfmtCompensLeaveCom();
        setting.saveToMemento(new JpaCompensatoryLeaveComSetMemento(entity));
        return entity;
    }
    
    /**
     * Find normal.
     *
     * @param companyId the company id
     * @return the kmfmt normal vacation set
     */
    private KmfmtNormalVacationSet findNormal(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtNormalVacationSet> query = builder.createQuery(KmfmtNormalVacationSet.class);
        Root<KmfmtNormalVacationSet> root = query.from(KmfmtNormalVacationSet.class);
        
        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtNormalVacationSet_.cid), companyId));
        
        return em.createQuery(query).getSingleResult();
    }
    
    /**
     * Find occur vacation.
     *
     * @param companyId the company id
     * @return the kmfmt occur vacation set
     */
    private KmfmtOccurVacationSet findOccurVacation(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtOccurVacationSet> query = builder.createQuery(KmfmtOccurVacationSet.class);
        Root<KmfmtOccurVacationSet> root = query.from(KmfmtOccurVacationSet.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtOccurVacationSet_.cid), companyId));

        return em.createQuery(query).getSingleResult();
    }
}
