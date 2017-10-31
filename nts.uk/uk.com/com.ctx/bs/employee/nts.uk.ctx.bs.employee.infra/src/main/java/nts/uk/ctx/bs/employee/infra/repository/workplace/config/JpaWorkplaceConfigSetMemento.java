/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigSetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig;

/**
 * The Class JpaWorkplaceConfigSetMemento.
 */
public class JpaWorkplaceConfigSetMemento implements WorkplaceConfigSetMemento {

    /** The lst entity. */
    private List<BsymtWkpConfig> lstEntity;

    /**
     * Instantiates a new jpa workplace config set memento.
     *
     * @param lstEntity
     *            the lst entity
     */
    public JpaWorkplaceConfigSetMemento(List<BsymtWkpConfig> lstEntity) {
        this.lstEntity = lstEntity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigSetMemento#
     * setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.lstEntity.forEach(entity -> {
            entity.getBsymtWkpConfigPK().setCid(companyId);
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigSetMemento#
     * setWkpConfigHistory(java.util.List)
     */
    @Override
    public void setWkpConfigHistory(List<WorkplaceConfigHistory> lstWkpConfigHistory) {
        Map<String, WorkplaceConfigHistory> mapWkpConfigHist = lstWkpConfigHistory.stream()
                .collect(Collectors.toMap(item -> ((WorkplaceConfigHistory) item).identifier(), Function.identity()));
        this.lstEntity.forEach(entity -> {
            WorkplaceConfigHistory wkpConfigHist = mapWkpConfigHist.get(entity.getBsymtWkpConfigPK().getHistoryId());
            JpaWorkplaceConfigHistorySetMemento memento = new JpaWorkplaceConfigHistorySetMemento(entity);
            wkpConfigHist.saveToMemento(memento);
        });
    }

}
