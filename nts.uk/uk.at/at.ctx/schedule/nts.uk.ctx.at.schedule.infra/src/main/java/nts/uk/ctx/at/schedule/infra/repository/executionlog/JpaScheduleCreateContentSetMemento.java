/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import lombok.val;
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
		this.entity.setCreationMethod(specifyCreation.getCreationMethod().value);
		this.entity.setCopyStartYmd(specifyCreation.getCopyStartDate().orElse(null));
		this.entity.setReferenceMaster(specifyCreation.getReferenceMaster().isPresent()
				? specifyCreation.getReferenceMaster().get().value: null);
		this.entity.setMonthlyPatternId(specifyCreation.getMonthlyPatternCode().isPresent()?
				specifyCreation.getMonthlyPatternCode().get().v(): null);


	}

	@Override
	public void setRecreateCondition(Optional<RecreateCondition> recreateCondition) {
		if (!recreateCondition.isPresent()){
			return;
		}
		val newRecreateCondition = recreateCondition.get();
		this.entity.setReTargetAtr(newRecreateCondition.getReTargetAtr());
		this.entity.setReOverwriteConfirmed(newRecreateCondition.getReOverwriteConfirmed());
		this.entity.setReOverwriteRevised(newRecreateCondition.getReOverwriteRevised());
		if (newRecreateCondition.getNarrowingEmployees().isPresent()){
			this.entity.setReTargetTransfer(newRecreateCondition.getNarrowingEmployees().get().isTransfer());
			this.entity.setReTargetLeave(newRecreateCondition.getNarrowingEmployees().get().isLeaveOfAbsence());
			this.entity.setReTargetShortWork(newRecreateCondition.getNarrowingEmployees().get().isShortWorkingHours());
			this.entity.setReTargetLaborChange(newRecreateCondition.getNarrowingEmployees().get().isChangedWorkingConditions());

		}

	}

	@Override
	public void setCopyStartDate(GeneralDate copyStartDate) {
		this.entity.setCopyStartYmd(copyStartDate);
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
