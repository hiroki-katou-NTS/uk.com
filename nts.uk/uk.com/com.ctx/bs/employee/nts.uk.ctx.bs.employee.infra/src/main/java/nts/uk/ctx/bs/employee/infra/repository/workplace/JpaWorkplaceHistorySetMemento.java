package nts.uk.ctx.bs.employee.infra.repository.workplace;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento;

@Stateless
public class JpaWorkplaceHistorySetMemento implements WorkplaceHistorySetMemento {

    @Override
    public void setHistoryId(HistoryId historyId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPeriod(Period period) {
        // TODO Auto-generated method stub
        
    }

}
