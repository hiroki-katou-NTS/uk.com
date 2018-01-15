/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.timeunit;

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
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZone;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZoneRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTimePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTimePK_;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTime_;

/**
 * The Class JpaExtBudgetTimeZoneRepository.
 */
@Stateless
public class JpaExtBudgetTimeZoneRepository extends JpaRepository implements ExternalBudgetTimeZoneRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetTimeZoneRepository#add(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresult.ExternalBudgetTimeZone)
     */
    @Override
    public <T> void add(ExternalBudgetTimeZone<T> domain) {
        // find list entity
        List<KscdtExtBudgetTime> lstEntity = this.findListEntity(domain);
        
        // insert list entity
        lstEntity.forEach(entity -> {
            this.commandProxy().insert(entity);
            this.getEntityManager().flush();
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetTimeZoneRepository#update(nts.uk.ctx.at.schedule.dom.budget
     * .external.actualresult.ExternalBudgetTimeZone)
     */
    @Override
    public <T> void update(ExternalBudgetTimeZone<T> domain) {
        // find list entity
        List<KscdtExtBudgetTime> lstEntity = this.findListEntity(domain);
        
        // update list entity
        lstEntity.forEach(entity -> {
            this.commandProxy().update(entity);
            this.getEntityManager().flush();
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetTimeZoneRepository#isExisted(java.lang.String,
     * nts.arc.time.GeneralDate, java.lang.String)
     */
    @Override
    public boolean isExisted(String workplaceId, GeneralDate actualDate, String extBudgetCode) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KscdtExtBudgetTime> query = builder.createQuery(KscdtExtBudgetTime.class);
        Root<KscdtExtBudgetTime> root = query.from(KscdtExtBudgetTime.class);

        // add list condition
        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(
                root.get(KscdtExtBudgetTime_.kscdtExtBudgetTimePK).get(KscdtExtBudgetTimePK_.wkpid), workplaceId));
        predicateList.add(builder.equal(
                root.get(KscdtExtBudgetTime_.kscdtExtBudgetTimePK).get(KscdtExtBudgetTimePK_.actualDate), actualDate));
        predicateList.add(
                builder.equal(root.get(KscdtExtBudgetTime_.kscdtExtBudgetTimePK).get(KscdtExtBudgetTimePK_.extBudgetCd),
                        extBudgetCode));

        query.where(predicateList.toArray(new Predicate[] {}));
        
        return !CollectionUtil.isEmpty(em.createQuery(query).getResultList());
    }
    
    /**
     * Find list entity.
     *
     * @param <T>
     *            the generic type
     * @param domain
     *            the domain
     * @return the list
     */
    private <T> List<KscdtExtBudgetTime> findListEntity(ExternalBudgetTimeZone<T> domain) {
        // create template entity.
        KscdtExtBudgetTimePK tempPK = new KscdtExtBudgetTimePK(domain.getWorkplaceId(),
                GeneralDate.legacyDate(domain.getActualDate()), domain.getExtBudgetCode().v());
        
        List<KscdtExtBudgetTime> lstEntity = new ArrayList<>();
        
        for (ExternalBudgetTimeZoneVal<T> object : domain.getActualValues()) {

            // create new other pk
            KscdtExtBudgetTimePK pk = KscdtExtBudgetTimePK.createEntity(tempPK);
            pk.setPeriodTimeNo(object.getTimePeriod());

            KscdtExtBudgetTime newEntity = new KscdtExtBudgetTime(pk);
            // find entity existed ?
            Optional<KscdtExtBudgetTime> optional = this.queryProxy().find(pk, KscdtExtBudgetTime.class);
            if (optional.isPresent()) {
                newEntity = optional.get();
            }
            lstEntity.add(newEntity);
        }
        JpaExtBudgTimeZoneSetMemento<T> memento = new JpaExtBudgTimeZoneSetMemento<T>(lstEntity);
        domain.saveToMemento(memento);
        return lstEntity;
    }

}
