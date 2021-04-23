/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.acceptance.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.log.KscdtExtBudgetLog;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.log.KscdtExtBudgetLog_;

/**
 * The Class JpaExternalBudgetLogRepository.
 */
@Stateless
public class JpaExternalBudgetLogRepository extends JpaRepository implements ExternalBudgetLogRepository {
    
    /** The Constant HOUR_FIRST_DAY. */
    private static final int HOUR_FIRST_DAY = 0;
    
    /** The Constant HOUR_LAST_DAY. */
    private static final int HOUR_LAST_DAY = 23;

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
     * ExternalBudgetLogRepository#update(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresult.ExternalBudgetLog)
     */
    @Override
    public void update(ExternalBudgetLog domain) {
        this.commandProxy().update(this.toEntity(domain));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogRepository#findExtBudgetLogByExecuteId(java.lang.String)
     */
    @Override
    public Optional<ExternalBudgetLog> findExtBudgetLogByExecuteId(String executeId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KscdtExtBudgetLog> query = builder.createQuery(KscdtExtBudgetLog.class);
        Root<KscdtExtBudgetLog> root = query.from(KscdtExtBudgetLog.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(root.get(KscdtExtBudgetLog_.exeId), executeId));

        query.where(predicateList.toArray(new Predicate[] {}));

        return em.createQuery(query).getResultList().stream()
                .map(entity -> new ExternalBudgetLog(new JpaExternalBudgetLogGetMemento((KscdtExtBudgetLog) entity)))
                .findFirst();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogRepository#findExternalBudgetLog(java.lang.String,
     * java.lang.String, nts.arc.time.GeneralDate)
     */
    @Override
    public List<ExternalBudgetLog> findExternalBudgetLog(String employeeId, GeneralDateTime startDateTime,
            GeneralDateTime endDateTime, List<Integer> listState) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KscdtExtBudgetLog> query = builder.createQuery(KscdtExtBudgetLog.class);
        Root<KscdtExtBudgetLog> root = query.from(KscdtExtBudgetLog.class);

        // add list condition to query.
        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(root.get(KscdtExtBudgetLog_.sid), employeeId));
        predicateList.add(builder.greaterThanOrEqualTo(root.get(KscdtExtBudgetLog_.strDateTime),
                this.getGeneralDateTime(startDateTime, HOUR_FIRST_DAY)));
        predicateList.add(builder.lessThanOrEqualTo(root.get(KscdtExtBudgetLog_.strDateTime),
                this.getGeneralDateTime(endDateTime, HOUR_LAST_DAY)));
        
        Expression<Integer> exp = root.get(KscdtExtBudgetLog_.completionAtr);
        predicateList.add(exp.in(listState));

        query.where(predicateList.toArray(new Predicate[] {}));
        
        // order by start date time DESC
        query.orderBy(builder.desc(root.get(KscdtExtBudgetLog_.strDateTime)));

        return em.createQuery(query).getResultList().stream()
                .map(entity -> new ExternalBudgetLog(new JpaExternalBudgetLogGetMemento((KscdtExtBudgetLog) entity)))
                .collect(Collectors.toList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogRepository#isExisted(java.lang.String)
     */
    @Override
    public boolean isExisted(String executeId) {
        return this.queryProxy().find(executeId, KscdtExtBudgetLog.class).isPresent();
    }

    
    /**
     * To entity.
     *
     * @param domain the domain
     * @return the kscdt ext budget log
     */
    private KscdtExtBudgetLog toEntity(ExternalBudgetLog domain) {
        Optional<KscdtExtBudgetLog> optional = this.queryProxy().find(domain.getExecutionId(), KscdtExtBudgetLog.class);
        KscdtExtBudgetLog entity = new KscdtExtBudgetLog();
        if (optional.isPresent()) {
            entity = optional.get();
        }
        JpaExternalBudgetLogSetMemento memento = new JpaExternalBudgetLogSetMemento(entity);
        domain.saveToMemento(memento);
        return entity;
    }
    
    /**
     * Gets the general date time.
     *
     * @param dateTime the date time
     * @param hour the hour
     * @return the general date time
     */
    private GeneralDateTime getGeneralDateTime(GeneralDateTime dateTime, Integer hour) {
        int minute = 0, second = 0;
        
        // case 23:59 (not case 00:00)
        if (hour > 0) {
            minute = 59;
        }
        return GeneralDateTime.ymdhms(dateTime.year(), dateTime.month(), dateTime.day(), hour, minute, second);
    }

}
