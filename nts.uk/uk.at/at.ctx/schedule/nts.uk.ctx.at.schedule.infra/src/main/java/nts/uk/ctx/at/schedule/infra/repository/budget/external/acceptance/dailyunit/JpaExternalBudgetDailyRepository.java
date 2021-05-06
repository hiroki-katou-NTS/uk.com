/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.acceptance.dailyunit;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDailyRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.dailyunit.KscdtExtBudgetDaily;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.dailyunit.KscdtExtBudgetDailyPK;

/**
 * The Class JpaExternalBudgetDailyRepository.
 */
@Stateless
public class JpaExternalBudgetDailyRepository extends JpaRepository implements ExternalBudgetDailyRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyRepository#add(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresult.ExternalBudgetDaily)
     */
    @Override
    public <T> void add(ExternalBudgetDaily<T> domain) {
        this.commandProxy().insert(this.toEntity(domain));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyRepository#update(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresult.ExternalBudgetDaily)
     */
    @Override
    public <T> void update(ExternalBudgetDaily<T> domain) {
        this.commandProxy().update(this.toEntity(domain));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyRepository#isExisted(java.lang.String,
     * nts.arc.time.GeneralDate, java.lang.String)
     */
    @Override
    public boolean isExisted(String workplaceId, GeneralDate actualDate, String extBudgetCd) {
        Optional<KscdtExtBudgetDaily> optional = this.queryProxy()
                .find(new KscdtExtBudgetDailyPK(workplaceId, actualDate, extBudgetCd), KscdtExtBudgetDaily.class);
        return optional.isPresent();
    }
    
    /**
     * To entity.
     *
     * @param <T>
     *            the generic type
     * @param domain
     *            the domain
     * @return the kbddt ext budget daily
     */
    private <T> KscdtExtBudgetDaily toEntity(ExternalBudgetDaily<T> domain) {
        // find entity existed ?
        Optional<KscdtExtBudgetDaily> optional = this.queryProxy().find(
                new KscdtExtBudgetDailyPK(domain.getWorkplaceId(), GeneralDate.legacyDate(domain.getActualDate()),
                		domain.getExtBudgetCode().v()), KscdtExtBudgetDaily.class);
        KscdtExtBudgetDaily entity = null;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            entity = new KscdtExtBudgetDaily();
        }
        
        JpaExternalBudgetDailySetMemento<T> memento = new JpaExternalBudgetDailySetMemento<T>(entity);
        domain.saveToMemento(memento);
        return entity;
    }

}
