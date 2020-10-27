/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import java.util.Objects;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWt;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtPK;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaWorkTimeSettingSetMemento.
 */
public class JpaWorkTimeSettingSetMemento implements WorkTimeSettingSetMemento {

	/** The entity. */
	private KshmtWt entity;

	/**
	 * Instantiates a new jpa work time setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkTimeSettingSetMemento(KshmtWt entity) {
		if(entity.getKshmtWtPK() == null){
			entity.setKshmtWtPK(new KshmtWtPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtWtPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setWorktimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorktimeCode(WorkTimeCode worktimeCode) {
		this.entity.getKshmtWtPK().setWorktimeCd(worktimeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setWorkTimeDivision(nts.uk.ctx.at.shared.dom.worktime.worktimeset.
	 * WorkTimeDivision)
	 */
	@Override
	public void setWorkTimeDivision(WorkTimeDivision workTimeDivision) {
		this.entity.setDailyWorkAtr(workTimeDivision.getWorkTimeDailyAtr().value);
		this.entity.setWorktimeSetMethod(workTimeDivision.getWorkTimeMethodSet().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setAbolishAtr(nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr)
	 */
	@Override
	public void setAbolishAtr(AbolishAtr abolishAtr) {
		this.entity.setAbolitionAtr(abolishAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setColorCode(nts.uk.ctx.at.shared.dom.common.color.ColorCode)
	 */
	@Override
	public void setColorCode(ColorCode colorCode) {
		this.entity.setColor(colorCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setWorkTimeDisplayName(nts.uk.ctx.at.shared.dom.worktime.worktimeset.
	 * WorkTimeDisplayName)
	 */
	@Override
	public void setWorkTimeDisplayName(WorkTimeDisplayName workTimeDisplayName) {
		this.entity.setName(workTimeDisplayName.getWorkTimeName().v());
		this.entity.setAbname(workTimeDisplayName.getWorkTimeAbName().v());
		this.entity.setSymbol(workTimeDisplayName.getWorkTimeSymbol().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setMemo(nts.uk.shr.com.primitive.Memo)
	 */
	@Override
	public void setMemo(Memo memo) {
		if (!Objects.isNull(memo)) {
			this.entity.setMemo(memo.v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setNote(nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote)
	 */
	@Override
	public void setNote(WorkTimeNote note) {
		if (!Objects.isNull(note)) {
			this.entity.setNote(note.v());
		}
	}

}
