package nts.uk.ctx.bs.employee.infra.repository.workplace.config;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistorySetMemento;

@Stateless
public class JpaWorkplaceConfigHistorySetMemento implements WorkplaceConfigHistorySetMemento {

    @Override
    public void setHistoryId(String historyId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPeriod(Period period) {
        // TODO Auto-generated method stub
        
    }

}
