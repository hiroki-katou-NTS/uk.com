package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtStampReflect;
import nts.uk.shr.com.time.TimeWithDayAttr;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDTStampReflectTimezoneSetMemento.
 */
public class JpaDTStampReflectTimezoneSetMemento implements StampReflectTimezoneSetMemento {

	/** The entity. */
	private KshmtDtStampReflect entity;

	/**
	 * Instantiates a new jpa DT stamp reflect timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDTStampReflectTimezoneSetMemento(KshmtDtStampReflect entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#setWorkNo(nts.uk.ctx.at.shared.dom.worktime.common.WorkNo)
	 */
	@Override
	public void setWorkNo(WorkNo workNo) {
		this.entity.getKshmtDtStampReflectPK().setWorkNo(workNo.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#setClassification(nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr)
	 */
	@Override
	public void setClassification(GoLeavingWorkAtr classification) {
		this.entity.setAtr(classification.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#setEndTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEndTime(TimeWithDayAttr endTime) {
		this.entity.setEndTime(endTime.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#setStartTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartTime(TimeWithDayAttr startTime) {
		this.entity.setStartTime(startTime.v());
	}

}
