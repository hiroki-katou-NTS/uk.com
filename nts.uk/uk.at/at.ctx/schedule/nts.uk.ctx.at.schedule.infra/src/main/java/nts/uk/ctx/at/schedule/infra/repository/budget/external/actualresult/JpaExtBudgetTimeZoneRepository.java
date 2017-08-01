/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZone;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetTime;

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
        List<KscdtExtBudgetTime> lstEntity = this.findListEntity(domain);
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
        List<KscdtExtBudgetTime> lstEntity = this.findListEntity(domain);
        lstEntity.forEach(entity -> {
            this.commandProxy().update(entity);
            this.getEntityManager().flush();
        });
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
        KscdtExtBudgetTime tempEntity = new KscdtExtBudgetTime(domain.getWorkplaceId(),
                GeneralDate.legacyDate(domain.getActualDate()), domain.getExtBudgetCode().v());
        
        List<KscdtExtBudgetTime> lstEntity = new ArrayList<>();
        
        for (ExternalBudgetTimeZoneVal<T> object : domain.getActualValues()) {

            // create new other entity
            KscdtExtBudgetTime newEntity = KscdtExtBudgetTime.createEntity(tempEntity);
            newEntity.getKscdtExtBudgetTimePK().setPeriodTimeNo(object.getTimePeriod());

            // find entity existed ?
            Optional<KscdtExtBudgetTime> optional = this.queryProxy().find(newEntity, KscdtExtBudgetTime.class);
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
