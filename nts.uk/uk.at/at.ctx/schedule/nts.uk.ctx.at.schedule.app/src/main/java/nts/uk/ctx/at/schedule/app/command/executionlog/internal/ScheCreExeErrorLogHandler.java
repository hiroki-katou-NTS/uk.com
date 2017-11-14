package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class ScheCreExeErrorLogHandler {
	
	/** The internationalization. */
	@Inject
	private I18NResourcesForUK internationalization;
	
	
	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;
	
	/**
	 * To schedule error log.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 * @return the schedule error log
	 */
	private ScheduleErrorLog toScheduleErrorLog(ScheduleCreatorExecutionCommand command, String employeeId,
			String messageId) {
		return new ScheduleErrorLog(new ScheduleErrorLogGetMemento() {

			/**
			 * Gets the execution id.
			 *
			 * @return the execution id
			 */
			@Override
			public String getExecutionId() {
				return command.getExecutionId();
			}

			/**
			 * Gets the error content.
			 *
			 * @return the error content
			 */
			@Override
			public String getErrorContent() {
				return messageId+" "+internationalization.localize(messageId).get();
			}

			/**
			 * Gets the employee id.
			 *
			 * @return the employee id
			 */
			@Override
			public String getEmployeeId() {
				return employeeId;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see nts.uk.ctx.at.schedule.dom.executionlog.
			 * ScheduleErrorLogGetMemento#getDate()
			 */
			@Override
			public GeneralDate getDate() {
				return command.getToDate();
			}

		});
	}
	
	/**
	 * Adds the error.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 */
	public void addError(ScheduleCreatorExecutionCommand command, String employeeId, String messageId) {
		
		// check exist error
		if (!this.checkExistError(command, employeeId)) {
			this.scheduleErrorLogRepository.add(this.toScheduleErrorLog(command, employeeId, messageId));
		}
	}
	
	/**
	 * Check exist error.
	 *
	 * @param employeeId the employee id
	 * @return true, if successful
	 */
	public boolean checkExistError(ScheduleCreatorExecutionCommand command, String employeeId) {
		List<ScheduleErrorLog> errorLogs = this.scheduleErrorLogRepository
				.findByEmployeeId(command.getContent().getExecutionId(), employeeId);
		
		// check empty list log error
		if (CollectionUtil.isEmpty(errorLogs)) {
			return false;
		}
		return true;
	}
}
