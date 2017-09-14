/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento;

@Getter
@Setter
public class WorkplaceHistoryDto {

	/** The history id. */
	//履歴ID
	private String historyId;

	/** The period. */
	//期間
	private PeriodDto period;
	
	public WorkplaceHistory toDomain()
	{
		return new WorkplaceHistory(new WorkplaceHistoryGetMementoImpl(this));
	}
	
	public class WorkplaceHistoryGetMementoImpl implements WorkplaceHistoryGetMemento{

		public static final String WKP_HIST_DATE_FORMAT = "yyyy-MM-dd";
		private WorkplaceHistoryDto workplaceHistoryDto;
		
		public WorkplaceHistoryGetMementoImpl(WorkplaceHistoryDto workplaceHistoryDto) {
			this.workplaceHistoryDto = workplaceHistoryDto;
		}

		@Override
		public HistoryId getHistoryId() {
			return new HistoryId(this.workplaceHistoryDto.getHistoryId());
		}

		@Override
		public Period getPeriod() {
			return new Period(
					GeneralDate.fromString(this.workplaceHistoryDto.getPeriod().getStartDate(), WKP_HIST_DATE_FORMAT),
					GeneralDate.fromString(this.workplaceHistoryDto.getPeriod().getEndDate(), WKP_HIST_DATE_FORMAT));
		}
	}
}
