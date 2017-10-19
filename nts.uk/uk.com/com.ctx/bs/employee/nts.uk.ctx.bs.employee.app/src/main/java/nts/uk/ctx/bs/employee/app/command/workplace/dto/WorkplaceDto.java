/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;

/**
 * The Class WorkplaceDto.
 */
@Setter
@Getter
public class WorkplaceDto {

    /** The workplace id. */
    private String workplaceId;
    
    /** The wkp history. */
    private WorkplaceHistoryDto wkpHistory;

    /**
     * To domain.
     *
     * @param companyId
     *            the company id
     * @return the workplace
     */
    public Workplace toDomain(String companyId) {
        return new Workplace(new WorkplaceGetMementoImpl(companyId, this));
    }

    /**
     * The Class WorkplaceGetMementoImpl.
     */
    class WorkplaceGetMementoImpl implements WorkplaceGetMemento {

        /** The company id. */
        private String companyId;

        /** The dto. */
        private WorkplaceDto dto;

        /**
         * Instantiates a new workplace get memento impl.
         *
         * @param companyId
         *            the company id
         * @param dto
         *            the dto
         */
        public WorkplaceGetMementoImpl(String companyId, WorkplaceDto dto) {
            this.companyId = companyId;
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getCompanyId
         * ()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#
         * getWorkplaceId()
         */
        @Override
        public String getWorkplaceId() {
            String wkpId = this.dto.workplaceId;
            if (StringUtil.isNullOrEmpty(this.dto.workplaceId, true)) {
                wkpId = IdentifierUtil.randomUniqueId();
            }
            return wkpId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#
         * getWorkplaceHistory()
         */
        @Override
        public List<WorkplaceHistory> getWorkplaceHistory() {
            return Arrays.asList(this.dto.wkpHistory.toDomain());
        }
    }
}
