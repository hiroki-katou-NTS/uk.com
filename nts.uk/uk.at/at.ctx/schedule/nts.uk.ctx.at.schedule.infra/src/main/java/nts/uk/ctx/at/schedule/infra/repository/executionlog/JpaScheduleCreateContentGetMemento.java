package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.RebuildTargetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeContent;

/**
 * The Class JpaExecutionContentGetMemento.
 */
public class JpaScheduleCreateContentGetMemento implements ScheduleCreateContentGetMemento {

	/** The Constant TRUE_VALUE. */
	public static final int TRUE_VALUE = 1;

	/** The entity. */
	private KscdtScheExeContent entity;

	/**
	 * Instantiates a new jpa execution content get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleCreateContentGetMemento(KscdtScheExeContent entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getCopyStartDate()
	 */
	@Override
	public GeneralDate getCopyStartDate() {
		return this.entity.getCopyStartYmd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getCreateMethodAtr()
	 */
	@Override
	public CreateMethodAtr getCreateMethodAtr() {
		return CreateMethodAtr.valueOf(this.entity.getCreateMethodAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getConfirm()
	 */
	@Override
	public Boolean getConfirm() {
		return this.entity.getConfirm() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getImplementAtr()
	 */
	@Override
	public ImplementAtr getImplementAtr() {
		return ImplementAtr.valueOf(this.entity.getImplementAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getProcessExecutionAtr()
	 */
	@Override
	public ProcessExecutionAtr getProcessExecutionAtr() {
		return ProcessExecutionAtr.valueOf(this.entity.getProcessExeAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getReCreateAtr()
	 */
	@Override
	public ReCreateAtr getReCreateAtr() {
		return ReCreateAtr.valueOf(this.entity.getReCreateAtr());
	}

	//
	@Override
	public RebuildTargetAtr getRebuildTargetAtr() {
		return RebuildTargetAtr.valueOf(this.entity.getReTargetAtr());
	}

	@Override
	public Boolean getRecreateConverter() {
		return this.entity.getReConverter() == TRUE_VALUE;
	}

	@Override
	public Boolean getRecreateEmployeeOffWork() {
		return this.entity.getReEmpOffWork() == TRUE_VALUE;
	}

	@Override
	public Boolean getRecreateDirectBouncer() {
		return this.entity.getReDirectBouncer() == TRUE_VALUE;
	}

	@Override
	public Boolean getRecreateShortTermEmployee() {
		return this.entity.getReShortTermEmp() == TRUE_VALUE;
	}

	@Override
	public Boolean getRecreateWorkTypeChange() {
		return this.entity.getReWorkTypeChange() == TRUE_VALUE;
	}

	@Override
	public Boolean getProtectHandCorrection() {
		return this.entity.getReProtectHandCorrect() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getResetMasterInfo()
	 */
	@Override
	public Boolean getResetMasterInfo() {
		return this.entity.getReMasterInfo() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getResetWorkingHours()
	 */
	@Override
	public Boolean getResetWorkingHours() {
		return this.entity.getReWorkingHours() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getResetTimeAssignment()
	 */
	@Override
	public Boolean getResetTimeAssignment() {
		return this.entity.getReTimeAssignment() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getResetTimeChildCare()
	 */
	@Override
	public Boolean getResetStartEndTime() {
		return this.entity.getReStartEndTime() == TRUE_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentGetMemento#
	 * getExecutionId()
	 */
	@Override
	public String getExecutionId() {
		return this.entity.getExeId();
	}

}
