package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.*;

import java.util.Optional;

/**
 * The Class ExecutionContentDto.
 */
@Setter
public class ScheduleCreateContentDto implements ScheduleCreateContentSetMemento {

	/** The execution id. */
	public String executionId;

	/** The copy start date. */
	public GeneralDate copyStartDate;

	/** The create method atr. */
	public Integer createMethodAtr;

	/** The confirm. */
	public Boolean confirm;

	/** The implement atr. */
	public Integer implementAtr;

	/** The process execution atr. */
	public Integer processExecutionAtr;
	
	/** The rebuild target atr. */
	public Integer rebuildTargetAtr;

	/** The re create atr. */
	public Integer reCreateAtr;

	/** The reset master info. */
	public Boolean resetMasterInfo;

	/** The reset working hours. */
	public Boolean resetWorkingHours;

	/** The reset time assignment. */
	public Boolean resetTimeAssignment;
	
	/** The resetStartEndTime. */
	public Boolean resetStartEndTime;
	
	/** The resetStartEndTime. */
	public Boolean recreateConverter;
	
	/** The recreateEmployeeOffWork. */
	public Boolean recreateEmployeeOffWork;
	
	/** The recreateDirectBouncer. */
	public Boolean recreateDirectBouncer;
	
	/** The recreateShortTermEmployee. */
	public Boolean recreateShortTermEmployee;
	
	/** The recreateWorkTypeChange. */
	public Boolean recreateWorkTypeChange;
	
	/** The protectHandCorrection. */
	public Boolean protectHandCorrection;
	
	/** The start date. */
	public GeneralDate startDate;
	
	/** The end date. */
	public GeneralDate endDate;
	
	/** The execution start. */
	public GeneralDateTime executionStart;
	
	/** The execution end. */
	public GeneralDateTime executionEnd;
	
	/** The count execution. */
	public Integer countExecution;
	
	/** The count error. */
	public Integer countError;

	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Override
	public void setcreationType(ImplementAtr creationType) {

	}

	@Override
	public void setSpecifyCreation(SpecifyCreation specifyCreation) {

	}

	@Override
	public void setRecreateCondition(Optional<RecreateCondition> recreateCondition) {

	}


}
