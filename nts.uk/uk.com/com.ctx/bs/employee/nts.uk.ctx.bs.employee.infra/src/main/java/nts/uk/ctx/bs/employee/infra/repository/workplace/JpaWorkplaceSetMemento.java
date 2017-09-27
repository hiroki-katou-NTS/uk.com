package nts.uk.ctx.bs.employee.infra.repository.workplace;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento;

@Stateless
public class JpaWorkplaceSetMemento implements WorkplaceSetMemento {

    @Override
    public void setCompanyId(String companyId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWorkplaceId(WorkplaceId workplaceId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWorkplaceHistory(List<WorkplaceHistory> workplaceHistory) {
        // TODO Auto-generated method stub
        
    }

}
