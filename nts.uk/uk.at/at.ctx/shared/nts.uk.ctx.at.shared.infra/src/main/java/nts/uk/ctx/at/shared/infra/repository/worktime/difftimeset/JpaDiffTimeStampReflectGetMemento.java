package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtStampReflect;

public class JpaDiffTimeStampReflectGetMemento implements DiffTimeStampReflectGetMemento {

	/** The entity. */
	private KshmtDtStampReflect entity;

	public JpaDiffTimeStampReflectGetMemento(KshmtDtStampReflect kshmtDtStampReflect) {
		this.entity = kshmtDtStampReflect;
	}

	@Override
	public StampReflectTimezone getStampReflectTimezone() {
		return new StampReflectTimezone(new JpaDTStampReflectTimezoneGetMemento(this.entity));
	}

	@Override
	public boolean isIsUpdateStartTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUpdStartTime());
	}

}
