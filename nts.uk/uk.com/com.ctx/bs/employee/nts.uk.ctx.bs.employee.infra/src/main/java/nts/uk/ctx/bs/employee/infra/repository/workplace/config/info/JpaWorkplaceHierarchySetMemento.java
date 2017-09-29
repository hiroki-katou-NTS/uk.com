package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchySetMemento;

@Stateless
public class JpaWorkplaceHierarchySetMemento implements WorkplaceHierarchySetMemento {

    @Override
    public void setWorkplaceId(WorkplaceId workplaceId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHierarchyCode(HierarchyCode hierarchyCode) {
        // TODO Auto-generated method stub
        
    }

}
