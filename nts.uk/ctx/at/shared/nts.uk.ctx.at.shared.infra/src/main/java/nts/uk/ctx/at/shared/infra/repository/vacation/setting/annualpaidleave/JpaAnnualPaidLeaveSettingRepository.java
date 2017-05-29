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
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet_;

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
        this.commandProxy().insert(this.toEntity(setting));
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
        CriteriaQuery<KmfmtAnnualPaidLeave> cq = builder.createQuery(KmfmtAnnualPaidLeave.class);
        Root<KmfmtAnnualPaidLeave> root = cq.from(KmfmtAnnualPaidLeave.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtAnnualPaidLeave_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));
        
        List<KmfmtAnnualPaidLeave> result = em.createQuery(cq).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        KmfmtAnnualPaidLeave entity = result.get(0);
        KmfmtMngAnnualSet entityManage = findManageByCompanyId(companyId);

        return new AnnualPaidLeaveSetting(new JpaAnnualPaidLeaveSettingGetMemento(entity, entityManage));
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt annual paid leave
     */
    private KmfmtAnnualPaidLeave toEntity(AnnualPaidLeaveSetting setting) {
        KmfmtAnnualPaidLeave entity = new KmfmtAnnualPaidLeave();
        setting.saveToMemento(new JpaAnnualPaidLeaveSettingSetMemento(entity));
        return entity;
    }
    
    /**
     * Find manage by company id.
     *
     * @param companyId the company id
     * @return the kmfmt mng annual set
     */
    private KmfmtMngAnnualSet findManageByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtMngAnnualSet> cq = builder.createQuery(KmfmtMngAnnualSet.class);
        Root<KmfmtMngAnnualSet> root = cq.from(KmfmtMngAnnualSet.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtMngAnnualSet_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        return em.createQuery(cq).getSingleResult();
    }
}
