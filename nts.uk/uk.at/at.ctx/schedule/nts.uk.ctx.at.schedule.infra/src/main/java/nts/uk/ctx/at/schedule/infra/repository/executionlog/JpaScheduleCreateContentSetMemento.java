/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.*;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeContent;

import java.util.Optional;

/**
 * The Class JpaExecutionContentSetMemento.
 */
public class JpaScheduleCreateContentSetMemento implements ScheduleCreateContentSetMemento {
	//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001

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
	 * setConfirm(java.lang.Boolean)
	 */
	@Override
	public void setConfirm(Boolean confirm) {
		this.entity.setBeConfirmed(confirm);
	}

	@Override
	public void setcreationType(ImplementAtr creationType) {
		this.entity.setCreationType(creationType.value);
	}

	@Override
	public void setSpecifyCreation(SpecifyCreation specifyCreation) {

	}

	@Override
	public void setRecreateCondition(RecreateCondition recreateCondition) {

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


}
