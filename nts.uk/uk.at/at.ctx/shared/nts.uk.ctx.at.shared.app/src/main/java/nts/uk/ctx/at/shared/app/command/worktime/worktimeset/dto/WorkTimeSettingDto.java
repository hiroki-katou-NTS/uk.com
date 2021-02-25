/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WorkTimeSettingDto.
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkTimeSettingDto implements WorkTimeSettingGetMemento {

	/** The worktime code. */
	public String worktimeCode;

	/** The work time division. */
	public WorkTimeDivisionDto workTimeDivision;

	/** The abolish atr. */
	public Boolean isAbolish;

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
		return AppContexts.user().companyId();
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
		return new WorkTimeDivision(WorkTimeDailyAtr.valueOf(this.workTimeDivision.getWorkTimeDailyAtr()),
				WorkTimeMethodSet.valueOf(this.workTimeDivision.getWorkTimeMethodSet()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento#getAbolishAtr()
	 */
	@Override
	public AbolishAtr getAbolishAtr() {
		if(this.isAbolish){
			return AbolishAtr.ABOLISH;
		}
		return AbolishAtr.NOT_ABOLISH;
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
		return WorkTimeDisplayName.builder().workTimeName(new WorkTimeName(this.workTimeDisplayName.getWorkTimeName()))
				.workTimeAbName(new WorkTimeAbName(this.workTimeDisplayName.getWorkTimeAbName()))
				.workTimeSymbol(new WorkTimeSymbol(this.workTimeDisplayName.getWorkTimeSymbol())).build();
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
