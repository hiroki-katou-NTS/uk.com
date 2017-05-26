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

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet_;

/**
 * The Class JpaManageAnnualSettingReposity.
 */
@Stateless
public class JpaManageAnnualSettingReposity extends JpaRepository implements ManageAnnualSettingRepository {
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingReposity#add(nts.uk.ctx.at.shared.dom.vacation.setting
     * .annualpaidleave.ManageAnnualSetting)
     */
    @Override
    public void add(ManageAnnualSetting setting) {
        this.commandProxy().insert(this.toEntity(setting));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingReposity#update(nts.uk.ctx.pr.core.dom.vacation.
     * setting.annualpaidleave.ManageAnnualSetting)
     */
    @Override
    public void update(ManageAnnualSetting setting) {
        this.commandProxy().update(this.toEntity(setting));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingReposity#findByCompanyId(java.lang.String)
     */
    @Override
    public ManageAnnualSetting findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtMngAnnualSet> cq = builder.createQuery(KmfmtMngAnnualSet.class);
        Root<KmfmtMngAnnualSet> root = cq.from(KmfmtMngAnnualSet.class);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfmtMngAnnualSet_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        KmfmtMngAnnualSet entity = em.createQuery(cq).getSingleResult();
        if (entity == null) {
            throw new BusinessException(new RawErrorMessage("Not found manage annual setting."));
        }
        return new ManageAnnualSetting(new JpaManageAnnualSettingGetMemento(entity));
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt mng annual set
     */
    private KmfmtMngAnnualSet toEntity(ManageAnnualSetting setting) {
        KmfmtMngAnnualSet entity = new KmfmtMngAnnualSet();
        setting.saveToMemento(new JpaManageAnnualSettingSetMemento(entity));
        return entity;
    }
}
