/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.history;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.command.workplace.dto.WorkplaceHistoryDto;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;

/**
 * The Class SaveWkpHistoryCommand.
 */
@Getter
@Setter
public class SaveWkpHistoryCommand {

    /** The is add mode. */
    private Boolean isAddMode;
    
    /** The workplace id. */
    private String workplaceId;

    /** The workplace history. */
    private WorkplaceHistoryDto workplaceHistory;

    /**
     * To domain.
     *
     * @param companyId the company id
     * @return the workplace
     */
    public Workplace toDomain(String companyId) {
        return new Workplace(new WorkplaceGetMementoImpl(companyId, this));
    }

    /**
     * The Class WorkplaceGetMementoImpl.
     */
    public class WorkplaceGetMementoImpl implements WorkplaceGetMemento {

        /** The company id. */
        private String companyId;

        /** The workplace command. */
        private SaveWkpHistoryCommand workplaceCommand;

        /**
         * Instantiates a new workplace get memento impl.
         *
         * @param companyId the company id
         * @param workplaceCommand the workplace command
         */
        public WorkplaceGetMementoImpl(String companyId, SaveWkpHistoryCommand workplaceCommand) {
            this.companyId = companyId;
            this.workplaceCommand = workplaceCommand;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceId()
         */
        @Override
        public String getWorkplaceId() {
            return this.workplaceCommand.getWorkplaceId();
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceHistory()
         */
        @Override
        public List<WorkplaceHistory> getWorkplaceHistory() {
            return Arrays.asList(this.workplaceCommand.getWorkplaceHistory().toDomain());
        }

    }
}
