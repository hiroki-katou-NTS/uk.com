package nts.uk.ctx.pr.core.app.find.vacation.setting.acquisitionrule;

import lombok.Builder;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.Priority;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionOrderSetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionType;

/**
 * The Class VacationAcquisitionOrderDto.
 */
@Builder
public class VacationAcquisitionOrderItemDto implements AcquisitionOrderSetMemento {
	
	/** The vacation type. */
	public int vacationType;
	
	/** The priority. */
	public int priority;

	@Override
	public void setVacationType(AcquisitionType vacationType) {
		this.vacationType = vacationType.value;		
	}

	@Override
	public void setPriority(Priority priority) {
		this.priority = priority.v();
		
	}
	
	
}
