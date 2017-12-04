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
import nts.uk.ctx.at.shared.infra.entity.worktime.worktimeset.KshmtWorkTimeSet;
import nts.uk.shr.com.primitive.Memo;

public class JpaWorkTimeSettingSetMemento implements WorkTimeSettingSetMemento {

	private KshmtWorkTimeSet entity;

	public JpaWorkTimeSettingSetMemento(KshmtWorkTimeSet entity) {
		this.entity = entity;
	}

	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtWorkTimeSetPK().setCid(companyId);
	}

	@Override
	public void setWorktimeCode(WorkTimeCode worktimeCode) {
		this.entity.getKshmtWorkTimeSetPK().setWorktimeCd(worktimeCode.v());
	}

	@Override
	public void setWorkTimeDivision(WorkTimeDivision workTimeDivision) {
		this.entity.setDailyWorkAtr(workTimeDivision.getWorkTimeDailyAtr().value);
		this.entity.setWorktimeSetMethod(workTimeDivision.getWorkTimeMethodSet().value);
	}

	@Override
	public void setAbolishAtr(AbolishAtr abolishAtr) {
		this.entity.setAbolitionAtr(abolishAtr.value);
	}

	@Override
	public void setColorCode(ColorCode colorCode) {
		this.entity.setColor(colorCode.v());
	}

	@Override
	public void setWorkTimeDisplayName(WorkTimeDisplayName workTimeDisplayName) {
		this.entity.setName(workTimeDisplayName.getWorkTimeName().v());
		this.entity.setAbName(workTimeDisplayName.getWorkTimeAbName().v());
		this.entity.setSymbol(workTimeDisplayName.getWorkTimeSymbol().v());
	}

	@Override
	public void setMemo(Memo memo) {
		if (!Objects.isNull(memo)) {
			this.entity.setMemo(memo.v());
		}
	}

	@Override
	public void setNote(WorkTimeNote note) {
		if (!Objects.isNull(note)) {
			this.entity.setNote(note.v());
		}
	}

}
