/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.info;

import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WkpCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceInfo;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceInfoPK;

/**
 * The Class JpaWorkplaceInfoSetMemento.
 */
public class JpaWorkplaceInfoSetMemento implements WorkplaceInfoSetMemento {

    /** The entity. */
    private BsymtWorkplaceInfo entity;

    /**
     * Instantiates a new jpa workplace info set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaWorkplaceInfoSetMemento(BsymtWorkplaceInfo entity) {
        if (entity != null && entity.getBsymtWorkplaceInfoPK() == null) {
            entity.setBsymtWorkplaceInfoPK(new BsymtWorkplaceInfoPK());
        }
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.getBsymtWorkplaceInfoPK().setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setHistoryId(nts.uk.ctx.bs.employee.dom.workplace.HistoryId)
     */
    @Override
    public void setHistoryId(String historyId) {
        this.entity.getBsymtWorkplaceInfoPK().setHistoryId(historyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setWorkplaceId(nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId)
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.entity.getBsymtWorkplaceInfoPK().setWkpid(workplaceId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setWorkplaceCode(nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceCode)
     */
    @Override
    public void setWorkplaceCode(WkpCode workplaceCode) {
        this.entity.setWkpcd(workplaceCode.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setWorkplaceName(nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName)
     */
    @Override
    public void setWorkplaceName(WorkplaceName workplaceName) {
        this.entity.setWkpName(workplaceName.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setWkpGenericName(nts.uk.ctx.bs.employee.dom.workplace.info.
     * WorkplaceGenericName)
     */
    @Override
    public void setWkpGenericName(WorkplaceGenericName wkpGenericName) {
        this.entity.setWkpGenericName(wkpGenericName.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setWkpDisplayName(nts.uk.ctx.bs.employee.dom.workplace.info.
     * WorkplaceDisplayName)
     */
    @Override
    public void setWkpDisplayName(WorkplaceDisplayName wkpDisplayName) {
        this.entity.setWkpDisplayName(wkpDisplayName.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoSetMemento#
     * setOutsideWkpCode(nts.uk.ctx.bs.employee.dom.workplace.info.
     * OutsideWorkplaceCode)
     */
    @Override
    public void setOutsideWkpCode(OutsideWorkplaceCode outsideWkpCode) {
        this.entity.setWkpOutsideCode(outsideWkpCode.v());
    }

}
