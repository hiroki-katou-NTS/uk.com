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
import nts.uk.ctx.at.shared.infra.entity.worktime.worktimeset.KshmtWorkTimeSet;
import nts.uk.shr.com.primitive.Memo;

public class JpaWorkTimeSettingGetMemento implements WorkTimeSettingGetMemento {

	private KshmtWorkTimeSet entity;

	public JpaWorkTimeSettingGetMemento(KshmtWorkTimeSet entity) {
		this.entity = entity;
	}

	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWorkTimeSetPK().getCid();
	}

	@Override
	public WorkTimeCode getWorktimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWorkTimeSetPK().getWorktimeCd());
	}

	@Override
	public WorkTimeDivision getWorkTimeDivision() {
		return WorkTimeDivision.builder()
				.workTimeDailyAtr(WorkTimeDailyAtr.valueOf(this.entity.getDailyWorkAtr()))
				.workTimeMethodSet(WorkTimeMethodSet.valueOf(this.entity.getWorktimeSetMethod()))
				.build();
	}

	@Override
	public AbolishAtr getAbolishAtr() {
		return AbolishAtr.valueOf(this.entity.getAbolitionAtr());
	}

	@Override
	public ColorCode getColorCode() {
		return new ColorCode(this.entity.getColor());
	}

	@Override
	public WorkTimeDisplayName getWorkTimeDisplayName() {
		return WorkTimeDisplayName.builder()
				.workTimeName(new WorkTimeName(this.entity.getName()))
				.workTimeAbName(new WorkTimeAbName(this.entity.getAbName()))
				.workTimeSymbol(new WorkTimeSymbol(this.entity.getSymbol()))
				.build();
	}

	@Override
	public Memo getMemo() {
		return new Memo(this.entity.getMemo());
	}

	@Override
	public WorkTimeNote getNote() {
		return new WorkTimeNote(this.entity.getNote());
	}

}
