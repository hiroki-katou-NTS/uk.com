/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento;

@Getter
@Setter
public class WorkplaceHistoryDto {

	public static final String WKP_HIST_DATE_FORMAT = "yyyy-MM-dd";
	
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
			return new Period(this.workplaceHistoryDto.getPeriod().getStartDate(),
					this.workplaceHistoryDto.getPeriod().getEndDate());
		}
	}
}
