/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.RebuildTargetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeContent;

/**
 * The Class JpaExecutionContentSetMemento.
 */
public class JpaScheduleCreateContentSetMemento implements ScheduleCreateContentSetMemento {

	// YYYYMMDD
	/** The Constant MUL_YEAR. */
	public static final int MUL_YEAR = 10000;

	/** The Constant MUL_MONTH. */
	public static final int MUL_MONTH = 100;

	/** The Constant TRUE_VALUE. */
	public static final int TRUE_VALUE = 1;

	/** The Constant FALSE_VALUE. */
	public static final int FALSE_VALUE = 0;

	/** The entity. */
	private KscdtScheExeContent entity;

	/**
	 * Instantiates a new jpa execution content set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleCreateContentSetMemento(KscdtScheExeContent entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setCopyStartDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setCopyStartDate(GeneralDate copyStartDate) {
		this.entity.setCopyStartYmd(copyStartDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setCreateMethodAtr(nts.uk.ctx.at.schedule.dom.executionlog.
	 * CreateMethodAtr)
	 */
	@Override
	public void setCreateMethodAtr(CreateMethodAtr createMethodAtr) {
		this.entity.setCreateMethodAtr(createMethodAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setConfirm(java.lang.Boolean)
	 */
	@Override
	public void setConfirm(Boolean confirm) {
		this.entity.setConfirm(confirm ? TRUE_VALUE : FALSE_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setImplementAtr(nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr)
	 */
	@Override
	public void setImplementAtr(ImplementAtr implementAtr) {
		this.entity.setImplementAtr(implementAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setProcessExecutionAtr(nts.uk.ctx.at.schedule.dom.executionlog.
	 * ProcessExecutionAtr)
	 */
	@Override
	public void setProcessExecutionAtr(ProcessExecutionAtr processExecutionAtr) {
		this.entity.setProcessExeAtr(processExecutionAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setReCreateAtr(nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr)
	 */
	@Override
	public void setReCreateAtr(ReCreateAtr reCreateAtr) {
		this.entity.setReCreateAtr(reCreateAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setResetMasterInfo(java.lang.Boolean)
	 */
	@Override
	public void setResetMasterInfo(Boolean resetMasterInfo) {
		this.entity.setReMasterInfo(resetMasterInfo ? TRUE_VALUE : FALSE_VALUE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setResetWorkingHours(java.lang.Boolean)
	 */
	@Override
	public void setResetWorkingHours(Boolean resetWorkingHours) {
		this.entity.setReWorkingHours(resetWorkingHours ? TRUE_VALUE : FALSE_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentSetMemento#
	 * setResetTimeAssignment(java.lang.Boolean)
	 */
	@Override
	public void setResetTimeAssignment(Boolean resetTimeAssignment) {
		this.entity.setReTimeAssignment(resetTimeAssignment ? TRUE_VALUE : FALSE_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentSetMemento#
	 * setExecutionId(java.lang.String)
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.entity.setExeId(executionId);
	}

	@Override
	public void setResetStartEndTime(Boolean resetStartEndTime) {
		this.entity.setReStartEndTime(resetStartEndTime ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setRebuildTargetAtr(RebuildTargetAtr rebuildTargetAtr) {
		this.entity.setReTargetAtr(rebuildTargetAtr.value);
	}

	@Override
	public void setRecreateConverter(Boolean recreateConverter) {
		this.entity.setReConverter(recreateConverter ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setRecreateEmployeeOffWork(Boolean recreateEmployeeOffWork) {
		this.entity.setReEmpOffWork(recreateEmployeeOffWork ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setRecreateDirectBouncer(Boolean recreateDirectBouncer) {
		this.entity.setReDirectBouncer(recreateDirectBouncer ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setRecreateShortTermEmployee(Boolean recreateShortTermEmployee) {
		this.entity.setReShortTermEmp(recreateShortTermEmployee ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setRecreateWorkTypeChange(Boolean recreateWorkTypeChange) {
		this.entity.setReWorkTypeChange(recreateWorkTypeChange ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public void setProtectHandCorrection(Boolean protectHandCorrection) {
		this.entity.setReProtectHandCorrect(protectHandCorrection ? TRUE_VALUE : FALSE_VALUE);
	}

}
