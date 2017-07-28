package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailySetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetDaily;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetDailyPK;

public class JpaExternalBudgetDailySetMemento<T> implements ExternalBudgetDailySetMemento<T> {
    
    private KscdtExtBudgetDaily entity;
    
    public JpaExternalBudgetDailySetMemento(KscdtExtBudgetDaily entity) {
        if (entity != null && entity.getKbddtExtBudgetDailyPK() == null) {
            entity.setKbddtExtBudgetDailyPK(new KscdtExtBudgetDailyPK());
        }
        this.entity = entity;
    }
    
    @Override
    public void setCompanyId(String companyId) {
        this.entity.getKbddtExtBudgetDailyPK().setCid(companyId);
    }

    @Override
    public void setActualValue(ExternalBudgetVal<T> actualValue) {
        this.entity.setActualVal(actualValue.getRawValue());
    }

    @Override
    public void setExtBudgetCode(ExternalBudgetCd extBudgetCode) {
        this.entity.setExtBudgetCd(extBudgetCode.v());
    }

    @Override
    public void setProcessDate(Date processDate) {
        this.entity.setProcessD(GeneralDate.legacyDate(processDate));
    }

    @Override
    public void setWorkplaceId(String workplaceId) {
        this.entity.getKbddtExtBudgetDailyPK().setWkpid(workplaceId);
    }

}
