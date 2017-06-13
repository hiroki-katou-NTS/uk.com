/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmamtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmamtMngAnnualSet_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeVacationSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeVacationSet_;

/**
 * The Class JpaAnnualPaidLeaveSettingRepository.
 */
@Stateless
public class JpaAnnualPaidLeaveSettingRepository extends JpaRepository implements AnnualPaidLeaveSettingRepository {
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingRepository#add(nts.uk.ctx.at.shared.dom.vacation.
     * setting.annualpaidleave.AnnualPaidLeaveSetting)
     */
    @Override
    public void add(AnnualPaidLeaveSetting setting) {
        KalmtAnnualPaidLeave v = this.toEntity(setting);
        this.commandProxy().insert(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingRepository#update(nts.uk.ctx.pr.core.dom.vacation.
     * setting.annualpaidleave.AnnualPaidLeaveSetting)
     */
    @Override
    public void update(AnnualPaidLeaveSetting setting) {
        this.commandProxy().update(this.toEntity(setting));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingRepository#findByCompanyId(java.lang.String)
     */
    @Override
    public AnnualPaidLeaveSetting findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KalmtAnnualPaidLeave> cq = builder.createQuery(KalmtAnnualPaidLeave.class);
        Root<KalmtAnnualPaidLeave> root = cq.from(KalmtAnnualPaidLeave.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KalmtAnnualPaidLeave_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));
        
        List<KalmtAnnualPaidLeave> result = em.createQuery(cq).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        KalmtAnnualPaidLeave entity = result.get(0);
        KmamtMngAnnualSet entityYear = findYearManageByCompanyId(companyId);
        KtvmtTimeVacationSet entityTime = findTimeManageByCompanyId(companyId);

        return new AnnualPaidLeaveSetting(new JpaAnnualPaidLeaveSettingGetMemento(entity, entityYear, entityTime));
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kalmt annual paid leave
     */
    private KalmtAnnualPaidLeave toEntity(AnnualPaidLeaveSetting setting) {
        KalmtAnnualPaidLeave entity = new KalmtAnnualPaidLeave();
        setting.saveToMemento(new JpaAnnualPaidLeaveSettingSetMemento(entity));
        return entity;
    }
    
    /**
     * Find year manage by company id.
     *
     * @param companyId the company id
     * @return the kmamt mng annual set
     */
    private KmamtMngAnnualSet findYearManageByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmamtMngAnnualSet> cq = builder.createQuery(KmamtMngAnnualSet.class);
        Root<KmamtMngAnnualSet> root = cq.from(KmamtMngAnnualSet.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmamtMngAnnualSet_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        return em.createQuery(cq).getSingleResult();
    }
    
    /**
     * Find time manage by company id.
     *
     * @param companyId the company id
     * @return the ktvmt time vacation set
     */
    private KtvmtTimeVacationSet findTimeManageByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KtvmtTimeVacationSet> cq = builder.createQuery(KtvmtTimeVacationSet.class);
        Root<KtvmtTimeVacationSet> root = cq.from(KtvmtTimeVacationSet.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KtvmtTimeVacationSet_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        return em.createQuery(cq).getSingleResult();
    }
}
