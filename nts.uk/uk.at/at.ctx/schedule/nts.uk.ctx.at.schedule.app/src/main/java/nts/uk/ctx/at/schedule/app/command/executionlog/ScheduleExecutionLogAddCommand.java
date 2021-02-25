/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.*;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class ScheduleExecutionLogAddCommand.
 */
@Getter
@Setter
public class ScheduleExecutionLogAddCommand {

	/** The period start date. */
	private GeneralDate periodStartDate;

	/** The period end date. */
	private GeneralDate periodEndDate;

	/** The implement atr. */
	private int implementAtr;

	/** The re create atr. */
	private int reCreateAtr;

	/** The process execution atr. */
	private int processExecutionAtr;

	/** The reTargetAtr. */
	private int reTargetAtr;

	/** The reset working hours. */
	private boolean resetWorkingHours;

	/** The reset master info. */
	private boolean resetMasterInfo;

	/** The reset time assignment. */
	private boolean reTimeAssignment;

	/** The reconverter. */
	private boolean reConverter;

	/** The reset start end time. */
	private boolean reStartEndTime;

	/** The reEmployeeOffWork. */
	private boolean reEmpOffWork;

	/** The reShortTermEmp. */
	private boolean reShortTermEmp;

	/** The reWorkTypeChange. */
	private boolean reWorkTypeChange;

	/** The reDirectBouncer. */
	private boolean reDirectBouncer;

	/** The protectHandCorrect. */
	private boolean protectHandCorrect;

	/** The confirm. */
	private boolean confirm;

	/** The create method atr. */
	private int createMethodAtr;

	/** The copy start date. */
	private GeneralDate copyStartDate;

	/** The employee ids. */
	private List<String> employeeIds;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @param employeeId
	 *            the employee id
	 * @param executionId
	 *            the execution id
	 * @return the schedule execution log
	 */
	public ScheduleExecutionLog toDomain(String companyId, String employeeId, String executionId) {
		return new ScheduleExecutionLog(new ScheduleExecutionLogSaveGetMementoImpl(companyId, executionId, employeeId));
	}

	/**
	 * To domain content.
	 *
	 * @param executionId
	 *            the execution id
	 * @return the schedule create content
	 */
	public ScheduleCreateContent toDomainContent(String executionId) {
		return new ScheduleCreateContent(new ScheduleCreateContentGetMementoImpl(executionId));
	}

	/**
	 * To domain creator.
	 *
	 * @param executionId
	 *            the execution id
	 * @return the list
	 */
	public List<ScheduleCreator> toDomainCreator(String executionId) {
		return this.employeeIds.stream().map(employeeId -> {
			ScheduleCreator domain = new ScheduleCreator(new ScheduleCreatorGetMemento() {

				/**
				 * Gets the execution status.
				 *
				 * @return the execution status
				 */
				@Override
				public ExecutionStatus getExecutionStatus() {
					return ExecutionStatus.NOT_CREATED;
				}

				/**
				 * Gets the execution id.
				 *
				 * @return the execution id
				 */
				@Override
				public String getExecutionId() {
					return executionId;
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
			});
			return domain;
		}).collect(Collectors.toList());
	}

	/**
	 * The Class ScheduleExecutionLogSaveGetMementoImpl.
	 */
	class ScheduleExecutionLogSaveGetMementoImpl implements ScheduleExecutionLogGetMemento {

		/** The company id. */
		private String companyId;

		/** The execution id. */
		private String executionId;

		/** The employee id. */
		private String employeeId;

		/**
		 * Instantiates a new schedule execution log save get memento impl.
		 *
		 * @param companyId
		 *            the company id
		 * @param executionId
		 *            the execution id
		 * @param employeeId
		 *            the employee id
		 */
		public ScheduleExecutionLogSaveGetMementoImpl(String companyId, String executionId, String employeeId) {
			this.companyId = companyId;
			this.executionId = executionId;
			this.employeeId = employeeId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleExecutionLogGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleExecutionLogGetMemento#getCompletionStatus()
		 */
		@Override
		public CompletionStatus getCompletionStatus() {
			return CompletionStatus.INCOMPLETE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleExecutionLogGetMemento#getExecutionId()
		 */
		@Override
		public String getExecutionId() {
			return executionId;
		}

		@Override
		public ExecutionDateTime getExecutionDateTime() {
			return new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleExecutionLogGetMemento#getExecutionEmployeeId()
		 */
		@Override
		public String getExecutionEmployeeId() {
			return employeeId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleExecutionLogGetMemento#getPeriod()
		 */
		@Override
		public DatePeriod getPeriod() {
			return new DatePeriod(periodStartDate, periodEndDate);
		}

		@Override
		public ExecutionAtr getExeAtr() {
			return ExecutionAtr.MANUAL;
		}

	}

	/**
	 * The Class ScheduleCreateContentGetMementoImpl.
	 */
	class ScheduleCreateContentGetMementoImpl implements ScheduleCreateContentGetMemento {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
		/** The execution id. */
		private String executionId;

		/**
		 * Instantiates a new schedule create content get memento impl.
		 *
		 * @param executionId
		 *            the execution id
		 */
		public ScheduleCreateContentGetMementoImpl(String executionId) {
			this.executionId = executionId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.
		 * ScheduleCreateContentGetMemento#getExecutionId()
		 */
		@Override
		public String getExecutionId() {
			return this.executionId;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
		 * getCopyStartDate()
		 */
		@Override
		public SpecifyCreation getSpecifyCreation() {
			return new SpecifyCreation(
					CreationMethod.valueOf(createMethodAtr),
					Optional.of(copyStartDate),
					Optional.empty(),
					Optional.empty()

			);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
		 * getCreateMethodAtr()
		 */
		@Override
		public Optional<RecreateCondition> getRecreateCondition() {
			return Optional.of(new RecreateCondition(
					false,
					false,
					false,
					Optional.empty()
			));
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
		 * getConfirm()
		 */
		@Override
		public Boolean getConfirm() {
			return confirm;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
		 * getImplementAtr()
		 */
		@Override
		public ImplementAtr getCreationType() {
			return ImplementAtr.valueOf(1);
		}
	}
}
