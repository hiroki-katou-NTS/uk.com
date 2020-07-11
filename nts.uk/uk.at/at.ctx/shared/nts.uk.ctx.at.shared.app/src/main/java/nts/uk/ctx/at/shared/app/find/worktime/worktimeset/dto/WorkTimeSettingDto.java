/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WorkTimeSettingDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeSettingDto implements WorkTimeSettingSetMemento {

	/** The company id. */
	public String companyId;

	/** The worktime code. */
	public String worktimeCode;

	/** The work time division. */
	public WorkTimeDivisionDto workTimeDivision;

	/** The is abolish. */
	public Boolean isAbolish;

	/** The color code. */
	public String colorCode;

	/** The work time display name. */
	public WorkTimeDisplayNameDto workTimeDisplayName;

	/** The memo. */
	public String memo;

	/** The note. */
	public String note;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
		this.worktimeCode = worktimeCode.v();
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
		this.workTimeDivision = WorkTimeDivisionDto.builder()
				.workTimeDailyAtr(workTimeDivision.getWorkTimeDailyAtr().value)
				.workTimeMethodSet(workTimeDivision.getWorkTimeMethodSet().value).build();
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
		this.isAbolish = (abolishAtr == AbolishAtr.ABOLISH);
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
		this.colorCode = colorCode.v();
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
		this.workTimeDisplayName = WorkTimeDisplayNameDto.builder()
				.workTimeName(workTimeDisplayName.getWorkTimeName().v())
				.workTimeAbName(workTimeDisplayName.getWorkTimeAbName().v())
				.workTimeSymbol(workTimeDisplayName.getWorkTimeSymbol().v()).build();
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
		this.memo = memo.v();
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
		this.note = note.v();
	}

}
