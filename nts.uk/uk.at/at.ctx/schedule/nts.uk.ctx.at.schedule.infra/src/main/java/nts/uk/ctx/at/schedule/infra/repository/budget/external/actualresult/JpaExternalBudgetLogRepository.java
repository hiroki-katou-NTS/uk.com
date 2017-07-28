/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetLog;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetLog_;

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
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogRepository#findExternalBudgetLog(java.lang.String,
     * java.lang.String, nts.arc.time.GeneralDate)
     */
    @Override
    public List<ExternalBudgetLog> findExternalBudgetLog(String companyId, String employeeId, GeneralDate startDate,
            List<Integer> listState) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KscdtExtBudgetLog> query = builder.createQuery(KscdtExtBudgetLog.class);
        Root<KscdtExtBudgetLog> root = query.from(KscdtExtBudgetLog.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(root.get(KscdtExtBudgetLog_.cid), companyId));
        predicateList.add(builder.equal(root.get(KscdtExtBudgetLog_.sid), employeeId));
        predicateList.add(builder.lessThanOrEqualTo(root.get(KscdtExtBudgetLog_.strD), startDate));
        predicateList.add(builder.greaterThanOrEqualTo(root.get(KscdtExtBudgetLog_.endD), startDate));
        
        Expression<Integer> exp = root.get(KscdtExtBudgetLog_.completionAtr);
        predicateList.add(exp.in(listState));

        query.where(predicateList.toArray(new Predicate[] {}));
        query.orderBy(builder.desc(root.get(KscdtExtBudgetLog_.strD)));

        return em.createQuery(query).getResultList().stream()
                .map(entity -> new ExternalBudgetLog(new JpaExternalBudgetLogGetMemento((KscdtExtBudgetLog) entity)))
                .collect(Collectors.toList());
    }

    /**
     * To entity.
     *
     * @param domain
     *            the domain
     * @return the kbldt ext budget log
     */
    private KscdtExtBudgetLog toEntity(ExternalBudgetLog domain) {
        KscdtExtBudgetLog entity = new KscdtExtBudgetLog();
        JpaExternalBudgetLogSetMemento memento = new JpaExternalBudgetLogSetMemento(entity);
        domain.saveToMemento(memento);
        return entity;
    }
}
