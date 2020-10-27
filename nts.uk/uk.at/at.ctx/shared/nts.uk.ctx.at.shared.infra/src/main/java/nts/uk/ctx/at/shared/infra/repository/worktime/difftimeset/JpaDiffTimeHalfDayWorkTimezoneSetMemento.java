package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDaySetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifPK;

public class JpaDiffTimeHalfDayWorkTimezoneSetMemento implements DiffTimeHalfDaySetMemento {

	private KshmtWtDif entity;

	private int type;

	public JpaDiffTimeHalfDayWorkTimezoneSetMemento(KshmtWtDif entity, int value) {
		this.entity = entity;
		if (this.entity.getKshmtWtDifPK() == null) {
			this.entity.setKshmtWtDifPK(new KshmtWtDifPK());
		}
		this.type = value;
	}

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		restTimezone.saveToMemento(new JpaDiffTimeRestTimezoneSetMemento(this.entity, this.type));
	}

	@Override
	public void setWorkTimezone(DiffTimezoneSetting workTimezone) {
		workTimezone.saveToMemento(new JpaDiffTimezoneSettingSetMemento(this.entity, this.type));
	}

	@Override
	public void setAmPmAtr(AmPmAtr amPmAtr) {
		this.type = amPmAtr.value;
	}

}
