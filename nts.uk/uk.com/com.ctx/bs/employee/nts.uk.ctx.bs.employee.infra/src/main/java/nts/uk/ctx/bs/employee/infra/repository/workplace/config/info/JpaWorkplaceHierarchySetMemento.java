/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchySetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;

/**
 * The Class JpaWorkplaceHierarchySetMemento.
 */
public class JpaWorkplaceHierarchySetMemento implements WorkplaceHierarchySetMemento {

    /** The entity. */
    private BsymtWkpConfigInfo entity;
    
    /**
     * Instantiates a new jpa workplace hierarchy set memento.
     *
     * @param entity the entity
     */
    public JpaWorkplaceHierarchySetMemento(BsymtWkpConfigInfo entity) {
        this.entity = entity;
    }
    
    /**
     * Sets the workplace id.
     *
     * @param workplaceId the new workplace id
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.entity.getBsymtWkpConfigInfoPK().setWkpid(workplaceId);
    }

    /**
     * Sets the hierarchy code.
     *
     * @param hierarchyCode the new hierarchy code
     */
    @Override
    public void setHierarchyCode(HierarchyCode hierarchyCode) {
        this.entity.setHierarchyCd(hierarchyCode.v());
    }

}
