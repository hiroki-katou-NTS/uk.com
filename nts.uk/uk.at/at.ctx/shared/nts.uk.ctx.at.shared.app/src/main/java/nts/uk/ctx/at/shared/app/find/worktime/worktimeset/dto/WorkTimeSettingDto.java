/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingSetMemento;
import nts.uk.shr.com.primitive.Memo;

public class WorkTimeSettingDto implements WorkTimeSettingSetMemento {

	/** The company id. */
	public String companyId;

	/** The worktime code. */
	public String worktimeCode;

	/** The work time division. */
	public WorkTimeDivisionDto workTimeDivision;

	/** The abolish atr. */
	public Integer abolishAtr;

	/** The color code. */
	public String colorCode;

	/** The work time display name. */
	public WorkTimeDisplayNameDto workTimeDisplayName;

	/** The memo. */
	public String memo;

	/** The note. */
	public String note;

	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public void setWorktimeCode(WorkTimeCode worktimeCode) {
		this.worktimeCode = worktimeCode.v();
	}

	@Override
	public void setWorkTimeDivision(WorkTimeDivision workTimeDivision) {
		this.workTimeDivision = WorkTimeDivisionDto.builder()
				.workTimeDailyAtr(workTimeDivision.getWorkTimeDailyAtr().value)
				.workTimeMethodSet(workTimeDivision.getWorkTimeMethodSet().value).build();
	}

	@Override
	public void setAbolishAtr(AbolishAtr abolishAtr) {
		this.abolishAtr = abolishAtr.value;
	}

	@Override
	public void setColorCode(ColorCode colorCode) {
		this.colorCode = colorCode.v();
	}

	@Override
	public void setWorkTimeDisplayName(WorkTimeDisplayName workTimeDisplayName) {
		this.workTimeDisplayName = WorkTimeDisplayNameDto.builder()
				.workTimeName(workTimeDisplayName.getWorkTimeName().v())
				.workTimeAbName(workTimeDisplayName.getWorkTimeAbName().v())
				.workTimeSymbol(workTimeDisplayName.getWorkTimeSymbol().v()).build();
	}

	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

	@Override
	public void setNote(WorkTimeNote note) {
		this.note = note.v();
	}

}
