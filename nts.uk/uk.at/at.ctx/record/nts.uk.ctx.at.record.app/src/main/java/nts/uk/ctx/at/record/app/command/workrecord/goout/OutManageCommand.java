package nts.uk.ctx.at.record.app.command.workrecord.goout;

import nts.uk.ctx.at.record.dom.workrecord.goout.MaxGoOut;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutManageCommand.
 *
 * @author hoangdd
 */
public class OutManageCommand implements OutManageGetMemento{

	/** The max usage. */
	private int maxUsage;
	
	/** The init value reason go out. */
	private int initValueReasonGoOut;
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	/**
	 * Gets the max usage.
	 *
	 * @return the max usage
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getMaxUsage()
	 */
	@Override
	public MaxGoOut getMaxUsage() {
		return new MaxGoOut(this.maxUsage);
	}

	/**
	 * Gets the inits the value reason go out.
	 *
	 * @return the inits the value reason go out
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getInitValueReasonGoOut()
	 */
	@Override
	public GoingOutReason getInitValueReasonGoOut() {
		return GoingOutReason.valueOf(this.initValueReasonGoOut);
	}

}

