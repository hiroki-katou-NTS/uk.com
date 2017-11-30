/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew;
import nts.uk.shr.com.primitive.Memo;

public class JpaWorkTimeSettingGetMemento implements WorkTimeSettingGetMemento {

	// TODO
	Object entity;

	public JpaWorkTimeSettingGetMemento(KwtstWorkTimeSetNew entity) {
		this.entity = entity;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimeCode getWorktimeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimeDivision getWorkTimeDivision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbolishAtr getAbolishAtr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColorCode getColorCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimeDisplayName getWorkTimeDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Memo getMemo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimeNote getNote() {
		// TODO Auto-generated method stub
		return null;
	}

}
