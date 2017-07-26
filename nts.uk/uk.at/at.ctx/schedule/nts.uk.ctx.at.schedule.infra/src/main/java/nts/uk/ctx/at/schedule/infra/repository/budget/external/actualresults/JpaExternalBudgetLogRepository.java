/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

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
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KbldtExtBudgetLog;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KbldtExtBudgetLogPK_;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KbldtExtBudgetLog_;

/**
 * The Class JpaExternalBudgetLogRepository.
 */
@Stateless
public class JpaExternalBudgetLogRepository extends JpaRepository implements ExternalBudgetLogRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogRepository#add(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresults.ExternalBudgetLog)
     */
    @Override
    public void add(ExternalBudgetLog domain) {
        this.commandProxy().insert(this.toEntity(domain));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogRepository#findAllByCompanyId(java.lang.String)
     */
    @Override
    public List<ExternalBudgetLog> findAllByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KbldtExtBudgetLog> query = builder.createQuery(KbldtExtBudgetLog.class);
        Root<KbldtExtBudgetLog> root = query.from(KbldtExtBudgetLog.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(root.get(KbldtExtBudgetLog_.kbldtExtBudgetLogPK).get(KbldtExtBudgetLogPK_.cid),
                companyId));

        query.where(predicateList.toArray(new Predicate[] {}));

        return em.createQuery(query).getResultList().stream()
                .map(entity -> {
                    return new ExternalBudgetLog(new JpaExternalBudgetLogGetMemento((KbldtExtBudgetLog) entity));
                }).collect(Collectors.toList());
    }

    /**
     * To entity.
     *
     * @param domain
     *            the domain
     * @return the kbldt ext budget log
     */
    private KbldtExtBudgetLog toEntity(ExternalBudgetLog domain) {
        Optional<KbldtExtBudgetLog> optinal = this.queryProxy()
                .find(new KbldtExtBudgetLog(domain.getCompanyId(), domain.getEmployeeId()), KbldtExtBudgetLog.class);
        KbldtExtBudgetLog entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KbldtExtBudgetLog();
        }
        JpaExternalBudgetLogSetMemento memento = new JpaExternalBudgetLogSetMemento(entity);
        domain.saveToMemento(memento);
        return entity;
    }
}
