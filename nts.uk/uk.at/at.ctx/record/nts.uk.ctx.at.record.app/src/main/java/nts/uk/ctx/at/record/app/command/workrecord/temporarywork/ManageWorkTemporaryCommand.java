/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.temporarywork;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.MaxUsage;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hoangdd
 *
 */
@NoArgsConstructor
public class ManageWorkTemporaryCommand implements ManageWorkTemporaryGetMemento{

	/** The max usage. */
	private int maxUsage;
	
	/** The time treat temporary same. */
	private int timeTreatTemporarySame;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getMaxUsage()
	 */
	@Override
	public MaxUsage getMaxUsage() {
		return new MaxUsage(this.maxUsage);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getTimeTreatTemporarySame()
	 */
	@Override
	public AttendanceTime getTimeTreatTemporarySame() {
		return new AttendanceTime(this.timeTreatTemporarySame);
	}
	
}

