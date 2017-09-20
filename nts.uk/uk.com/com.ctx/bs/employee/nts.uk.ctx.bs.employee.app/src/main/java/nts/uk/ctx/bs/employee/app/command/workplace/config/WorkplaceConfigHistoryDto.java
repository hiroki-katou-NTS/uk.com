/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.workplace.PeriodDto;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento;

@Getter
@Setter
public class WorkplaceConfigHistoryDto {

	/** The Constant WKP_CONFIG_HIST_DATE_FORMAT. */
	public static final String WKP_CONFIG_HIST_DATE_FORMAT = "yyyy-MM-dd";
	
	/** The history id. */
	// ID
	private String historyId;

	/** The period. */
	// 期間
	private PeriodDto period;

	/**
	 * To domain.
	 *
	 * @return the workplace config history
	 */
	public WorkplaceConfigHistory toDomain() {
		return new WorkplaceConfigHistory(new WorkplaceConfigHistoryGetMementoImpl(this));
	}

	/**
	 * The Class WorkplaceConfigHistoryGetMementoImpl.
	 */
	public class WorkplaceConfigHistoryGetMementoImpl implements WorkplaceConfigHistoryGetMemento {

		/** The workplace config history dto. */
		private WorkplaceConfigHistoryDto workplaceConfigHistoryDto;

		/**
		 * Instantiates a new workplace config history get memento impl.
		 *
		 * @param workplaceConfigHistoryDto the workplace config history dto
		 */
		public WorkplaceConfigHistoryGetMementoImpl(WorkplaceConfigHistoryDto workplaceConfigHistoryDto) {
			this.workplaceConfigHistoryDto = workplaceConfigHistoryDto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento#getHistoryId()
		 */
		@Override
		public String getHistoryId() {
			return this.workplaceConfigHistoryDto.getHistoryId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento#getPeriod()
		 */
		@Override
		public Period getPeriod() {
			return new Period(
					GeneralDate.fromString(this.workplaceConfigHistoryDto.getPeriod().getStartDate(),
							WKP_CONFIG_HIST_DATE_FORMAT),
					GeneralDate.fromString(this.workplaceConfigHistoryDto.getPeriod().getEndDate(),
							WKP_CONFIG_HIST_DATE_FORMAT));
		}

	}
}
