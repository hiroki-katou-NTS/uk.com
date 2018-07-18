/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

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
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave_;

/**
 * The Class JpaAnnualPaidLeaveSettingRepository.
 */
@Stateless
public class JpaAnnualPaidLeaveSettingRepository extends JpaRepository implements AnnualPaidLeaveSettingRepository {
    
    /** The element first. */
    private static final int ELEMENT_FIRST = 0;
    
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
        KalmtAnnualPaidLeave entity = result.get(ELEMENT_FIRST);

        return new AnnualPaidLeaveSetting(new JpaAnnualPaidLeaveSettingGetMemento(entity));
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kalmt annual paid leave
     */
    private KalmtAnnualPaidLeave toEntity(AnnualPaidLeaveSetting setting) {
        Optional<KalmtAnnualPaidLeave> optinal = this.queryProxy().find(setting.getCompanyId(),
                KalmtAnnualPaidLeave.class);
        KalmtAnnualPaidLeave entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KalmtAnnualPaidLeave();
        }
        setting.saveToMemento(new JpaAnnualPaidLeaveSettingSetMemento(entity));
        return entity;
    }

	@Override
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// TODO Auto-generated method stub
		
	}
    
}
