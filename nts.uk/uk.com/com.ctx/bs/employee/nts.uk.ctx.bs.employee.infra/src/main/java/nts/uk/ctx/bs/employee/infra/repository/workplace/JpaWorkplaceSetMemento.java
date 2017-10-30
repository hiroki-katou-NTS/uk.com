/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHistPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceSetMemento.
 */
public class JpaWorkplaceSetMemento implements WorkplaceSetMemento {

    /** The lst entity. */
    private List<BsymtWorkplaceHist> lstEntity;

    /**
     * Instantiates a new jpa workplace set memento.
     *
     * @param lstEntity
     *            the lst entity
     */
    public JpaWorkplaceSetMemento(List<BsymtWorkplaceHist> lstEntity) {
        this.beforeInitial(lstEntity);
        this.lstEntity = lstEntity;
    }

    /**
     * Before initial.
     *
     * @param lstEntity
     *            the lst entity
     */
    private void beforeInitial(List<BsymtWorkplaceHist> lstEntity) {
        lstEntity.forEach(entity -> {
            if (entity != null && entity.getBsymtWorkplaceHistPK() == null) {
                entity.setBsymtWorkplaceHistPK(new BsymtWorkplaceHistPK());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#setCompanyId(
     * java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.lstEntity.forEach(entity -> {
            entity.getBsymtWorkplaceHistPK().setCid(companyId);
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#setWorkplaceId(
     * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId)
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.lstEntity.forEach(entity -> {
            entity.getBsymtWorkplaceHistPK().setWkpid(workplaceId);
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#
     * setWorkplaceHistory(java.util.List)
     */
    @Override
    public void setWorkplaceHistory(List<WorkplaceHistory> lstWkpHistory) {
        // convert list workplace history to map by key historyId
        Map<String, DatePeriod> mapWkpHist = lstWkpHistory.stream().collect(Collectors.toMap(
                item -> ((WorkplaceHistory) item).getHistoryId(), item -> ((WorkplaceHistory) item).getPeriod()));

        // set period
        this.lstEntity.forEach(entity -> {
            DatePeriod period = mapWkpHist.get(entity.getBsymtWorkplaceHistPK().getHistoryId());
            entity.setStrD(period.start());
            entity.setEndD(period.end());
        });
    }

}
