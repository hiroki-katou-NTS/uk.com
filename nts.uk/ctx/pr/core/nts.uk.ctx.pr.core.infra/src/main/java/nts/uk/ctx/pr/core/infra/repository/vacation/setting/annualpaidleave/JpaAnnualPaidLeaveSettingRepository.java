/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.vacation.setting.annualpaidleave;

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
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave_;

@Stateless
public class JpaAnnualPaidLeaveSettingRepository extends JpaRepository implements AnnualPaidLeaveSettingRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingRepository#update(nts.uk.ctx.pr.core.dom.vacation.
     * setting.annualpaidleave.AnnualPaidLeaveSetting)
     */
    @Override
    public void update(AnnualPaidLeaveSetting setting) {
        KmfmtAnnualPaidLeave entity = new KmfmtAnnualPaidLeave();
        setting.saveToMemento(new JpaAnnualPaidLeaveSettingSetMemento(entity));
        this.commandProxy().update(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingRepository#findByCompanyId(java.lang.String)
     */
    @Override
    public Optional<AnnualPaidLeaveSetting> findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtAnnualPaidLeave> cq = builder.createQuery(KmfmtAnnualPaidLeave.class);
        Root<KmfmtAnnualPaidLeave> root = cq.from(KmfmtAnnualPaidLeave.class);
        
        List<Predicate> predicateList = new ArrayList<Predicate>();

        // TODO: check again predicate
        predicateList.add(builder.equal(root.get(KmfmtAnnualPaidLeave_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[] {}));
        
        List<KmfmtAnnualPaidLeave> result = em.createQuery(cq).getResultList();
        if (result.isEmpty()) {
            // TODO: throw exception
        }
        return Optional.of(result.stream()
                .map(item -> new AnnualPaidLeaveSetting(new JpaAnnualPaidLeaveSettingGetMemento(item)))
                .collect(Collectors.toList())
                .get(0));
    }

}
