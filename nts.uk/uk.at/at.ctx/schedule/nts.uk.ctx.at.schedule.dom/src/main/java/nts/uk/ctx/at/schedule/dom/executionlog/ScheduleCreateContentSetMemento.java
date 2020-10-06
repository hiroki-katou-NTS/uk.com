package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * The Interface ExecutionContentSetMemento.
 */
public interface ScheduleCreateContentSetMemento {

	/**
	 * Sets the execution id.
	 *
	 * @param executionId
	 *            the new execution id
	 */
	public void setExecutionId(String executionId);

	/**
	 * Sets the confirm.
	 *
	 * @param confirm
	 *            the new confirm
	 */
	public void setConfirm(Boolean confirm);

	/**
	 * Sets the implement atr.
	 *
	 * @param creationType
	 *            the new creation Type
	 */
	public void setcreationType(ImplementAtr creationType);

	/**
	 * Sets the specify Creation.
	 *
	 * @param specifyCreation
	 *            the new specify Creation
	 */
	public void setSpecifyCreation(SpecifyCreation specifyCreation);

	/**
	 * Sets the recreate Condition.
	 *
	 * @param recreateCondition
	 *            the new re recreate Condition
	 */
	public void setRecreateCondition(Optional<RecreateCondition> recreateCondition);

	/**
	 * Sets CopyStartDate
	 *
	 * @param copyStartDate
	 *            the new specify Creation
	 */
	public void setCopyStartDate(GeneralDate copyStartDate);

}
