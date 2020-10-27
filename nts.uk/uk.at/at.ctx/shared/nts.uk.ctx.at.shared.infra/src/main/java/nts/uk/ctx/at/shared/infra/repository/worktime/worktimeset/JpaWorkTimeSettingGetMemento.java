/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSymbol;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWt;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaWorkTimeSettingGetMemento.
 */
public class JpaWorkTimeSettingGetMemento implements WorkTimeSettingGetMemento {

	/** The entity. */
	private KshmtWt entity;

	/**
	 * Instantiates a new jpa work time setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimeSettingGetMemento(KshmtWt entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getWorktimeCode()
	 */
	@Override
	public WorkTimeCode getWorktimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getWorkTimeDivision()
	 */
	@Override
	public WorkTimeDivision getWorkTimeDivision() {
		return new WorkTimeDivision(WorkTimeDailyAtr.valueOf(this.entity.getDailyWorkAtr()),
				WorkTimeMethodSet.valueOf(this.entity.getWorktimeSetMethod()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getAbolishAtr()
	 */
	@Override
	public AbolishAtr getAbolishAtr() {
		return AbolishAtr.valueOf(this.entity.getAbolitionAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getColorCode()
	 */
	@Override
	public ColorCode getColorCode() {
		return new ColorCode(this.entity.getColor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getWorkTimeDisplayName()
	 */
	@Override
	public WorkTimeDisplayName getWorkTimeDisplayName() {
		return WorkTimeDisplayName.builder().workTimeName(new WorkTimeName(this.entity.getName()))
				.workTimeAbName(new WorkTimeAbName(this.entity.getAbname()))
				.workTimeSymbol(new WorkTimeSymbol(this.entity.getSymbol())).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getMemo()
	 */
	@Override
	public Memo getMemo() {
		return new Memo(this.entity.getMemo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#
	 * getNote()
	 */
	@Override
	public WorkTimeNote getNote() {
		return new WorkTimeNote(this.entity.getNote());
	}

}
