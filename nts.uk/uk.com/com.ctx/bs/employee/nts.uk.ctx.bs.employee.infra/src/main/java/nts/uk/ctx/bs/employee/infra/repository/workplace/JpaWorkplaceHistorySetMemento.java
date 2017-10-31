package nts.uk.ctx.bs.employee.infra.repository.workplace;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaWorkplaceHistorySetMemento implements WorkplaceHistorySetMemento {

    @Override
    public void setHistoryId(String historyId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPeriod(DatePeriod period) {
        // TODO Auto-generated method stub
        
    }

}
