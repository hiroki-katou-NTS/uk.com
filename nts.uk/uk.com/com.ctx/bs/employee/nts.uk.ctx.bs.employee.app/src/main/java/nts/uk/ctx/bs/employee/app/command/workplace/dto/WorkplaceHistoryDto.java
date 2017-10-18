/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceHistoryDto.
 */
@Getter
@Setter
public class WorkplaceHistoryDto {

    /** The history id. */
    private String historyId;

    /** The period. */
    private PeriodDto period;

    /**
     * To domain.
     *
     * @return the workplace history
     */
    public WorkplaceHistory toDomain() {
        return new WorkplaceHistory(new WorkplaceHistoryGetMementoImpl(this));
    }

    /**
     * The Class WorkplaceHistoryGetMementoImpl.
     */
    public class WorkplaceHistoryGetMementoImpl implements WorkplaceHistoryGetMemento {

        /** The workplace history dto. */
        private WorkplaceHistoryDto workplaceHistoryDto;

        /**
         * Instantiates a new workplace history get memento impl.
         *
         * @param workplaceHistoryDto the workplace history dto
         */
        public WorkplaceHistoryGetMementoImpl(WorkplaceHistoryDto workplaceHistoryDto) {
            this.workplaceHistoryDto = workplaceHistoryDto;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#getHistoryId()
         */
        @Override
        public HistoryId getHistoryId() {
            String historyId = this.workplaceHistoryDto.getHistoryId();
            if (StringUtil.isNullOrEmpty(this.workplaceHistoryDto.historyId, true)) {
                historyId = UUID.randomUUID().toString();
            }
            return new HistoryId(historyId);
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#getPeriod()
         */
        @Override
        public DatePeriod getPeriod() {
            return new DatePeriod(this.workplaceHistoryDto.getPeriod().getStartDate(),
                    this.workplaceHistoryDto.getPeriod().getEndDate());
        }
    }
}
