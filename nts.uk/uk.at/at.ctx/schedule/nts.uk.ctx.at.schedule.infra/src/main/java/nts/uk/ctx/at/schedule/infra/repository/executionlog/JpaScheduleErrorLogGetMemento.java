package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheErrLog;

/**
 * The Class JpaScheduleErrorLogGetMemento.
 */
public class JpaScheduleErrorLogGetMemento implements ScheduleErrorLogGetMemento {

	/** The entity. */
	private KscdtScheErrLog entity;

	/**
	 * Instantiates a new jpa schedule error log get memento.
	 *
	 * @param item the item
	 */
	public JpaScheduleErrorLogGetMemento(KscdtScheErrLog item) {
		this.entity = item;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento#getErrorContent()
	 */
	@Override
	public String getErrorContent() {
		return this.entity.getErrContent();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento#getExecutionId()
	 */
	@Override
	public String getExecutionId() {
		return this.entity.getKscdtScheErrLogPK().getExeId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento#getDate()
	 */
	@Override
	public GeneralDate getDate() {
		return this.entity.getKscdtScheErrLogPK().getYmd();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getKscdtScheErrLogPK().getSid();
	}

}
