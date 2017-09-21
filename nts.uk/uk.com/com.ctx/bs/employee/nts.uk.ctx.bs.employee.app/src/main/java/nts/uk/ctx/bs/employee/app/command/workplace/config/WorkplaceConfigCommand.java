/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;

/**
 * The Class WorkplaceConfigCommand.
 */
@Getter
@Setter
public class WorkplaceConfigCommand {

    /** The is add mode. */
    private Boolean isAddMode;

    /** The wkp config history. */
    // 履歴
    private WorkplaceConfigHistoryDto wkpConfigHistory;

    /**
     * To domain.
     *
     * @param companyId
     *            the company id
     * @return the workplace config
     */
    public WorkplaceConfig toDomain(String companyId) {
        return new WorkplaceConfig(new WorkplaceConfigGetMementoImpl(companyId, this));
    }

    /**
     * The Class WorkplaceConfigGetMementoImpl.
     */
    public class WorkplaceConfigGetMementoImpl implements WorkplaceConfigGetMemento {

        /** The company id. */
        private String companyId;

        /** The register workplace config command. */
        private WorkplaceConfigCommand registerWorkplaceConfigCommand;

        /**
         * Instantiates a new workplace config get memento impl.
         *
         * @param companyId
         *            the company id
         * @param registerWorkplaceConfigCommand
         *            the register workplace config command
         */
        public WorkplaceConfigGetMementoImpl(String companyId, WorkplaceConfigCommand registerWorkplaceConfigCommand) {
            this.companyId = companyId;
            this.registerWorkplaceConfigCommand = registerWorkplaceConfigCommand;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento
         * #getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento
         * #getWkpConfigHistory()
         */
        @Override
        public List<WorkplaceConfigHistory> getWkpConfigHistory() {
            return Arrays.asList(this.registerWorkplaceConfigCommand.getWkpConfigHistory().toDomain());
        }

    }
}
