package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtStampReflect;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JpaDTStampReflectTimezoneGetMemento implements StampReflectTimezoneGetMemento {

	private KshmtDtStampReflect entity;

	public JpaDTStampReflectTimezoneGetMemento(KshmtDtStampReflect entity) {
		this.entity = entity;
	}

	@Override
	public WorkNo getWorkNo() {
		return new WorkNo(this.entity.getKshmtDtStampReflectPK().getWorkNo());
	}

	@Override
	public GoLeavingWorkAtr getClassification() {
		return GoLeavingWorkAtr.valueOf(this.entity.getAtr());
	}

	@Override
	public TimeWithDayAttr getEndTime() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

	@Override
	public TimeWithDayAttr getStartTime() {
		return new TimeWithDayAttr(this.entity.getStartTime());
	}

}
