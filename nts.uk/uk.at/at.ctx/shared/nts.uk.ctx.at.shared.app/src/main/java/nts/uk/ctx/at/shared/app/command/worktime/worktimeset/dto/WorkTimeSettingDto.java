/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WorkTimeSettingDto.
 */
@Getter
@Setter
public class WorkTimeSettingDto implements WorkTimeSettingGetMemento {

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


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getWorktimeCode()
	 */
	@Override
	public WorkTimeCode getWorktimeCode() {
		return new WorkTimeCode(this.worktimeCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getWorkTimeDivision()
	 */
	@Override
	public WorkTimeDivision getWorkTimeDivision() {
		return WorkTimeDivision.builder()
				.workTimeDailyAtr(WorkTimeDailyAtr.valueOf(this.workTimeDivision.getWorkTimeDailyAtr()))
				.workTimeMethodSet(WorkTimeMethodSet.valueOf(this.workTimeDivision.getWorkTimeMethodSet())).build();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getAbolishAtr()
	 */
	@Override
	public AbolishAtr getAbolishAtr() {
		return AbolishAtr.valueOf(this.abolishAtr);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getColorCode()
	 */
	@Override
	public ColorCode getColorCode() {
		return new ColorCode(this.colorCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getWorkTimeDisplayName()
	 */
	@Override
	public WorkTimeDisplayName getWorkTimeDisplayName() {
		return WorkTimeDisplayName.builder().build();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getMemo()
	 */
	@Override
	public Memo getMemo() {
		return new Memo(this.memo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getNote()
	 */
	@Override
	public WorkTimeNote getNote() {
		return new WorkTimeNote(this.note);
	}
	

}
