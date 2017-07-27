/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailyRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KbddtExtBudgetDaily;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KbddtExtBudgetDailyPK;

/**
 * The Class JpaExternalBudgetDailyRepository.
 */
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

    /**
     * To entity.
     *
     * @param <T>
     *            the generic type
     * @param domain
     *            the domain
     * @return the kbddt ext budget daily
     */
    private <T> KbddtExtBudgetDaily toEntity(ExternalBudgetDaily<T> domain) {
        Optional<KbddtExtBudgetDaily> optinal = this.queryProxy().find(
                new KbddtExtBudgetDailyPK(domain.getCompanyId(), domain.getWorkplaceId()), KbddtExtBudgetDaily.class);
        KbddtExtBudgetDaily entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KbddtExtBudgetDaily();
        }
        JpaExternalBudgetDailySetMemento<T> memento = new JpaExternalBudgetDailySetMemento<T>(entity);
        domain.saveToMemento(memento);
        return entity;
    }
}
