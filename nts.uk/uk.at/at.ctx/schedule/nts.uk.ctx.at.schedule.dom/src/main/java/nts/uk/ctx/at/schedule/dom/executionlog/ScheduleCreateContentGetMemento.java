package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * The Interface ExecutionContentGetMemento.
 */
public interface ScheduleCreateContentGetMemento {

	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();

	/**
	 * Gets the confirm.
	 *
	 * @return the confirm
	 */
	public Boolean getConfirm();

	/**
	 * Gets the Creation Type.
	 *
	 * @return the implement atr
	 */
	public ImplementAtr getCreationType();

	/**
	 * Gets the Specify Creation.
	 *
	 * @return the copy Specify Creation
	 */
	public SpecifyCreation getSpecifyCreation();

	/**
	 * Gets the creates the method atr.
	 *
	 * @return the creates the method atr
	 */
	public Optional<RecreateCondition> getRecreateCondition();

}
