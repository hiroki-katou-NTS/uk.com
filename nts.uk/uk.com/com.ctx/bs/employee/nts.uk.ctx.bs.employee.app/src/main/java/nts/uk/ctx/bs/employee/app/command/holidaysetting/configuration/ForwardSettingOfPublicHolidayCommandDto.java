package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidayGetMemento;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayCarryOverDeadline;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ForwardSettingOfPublicHolidayCommandDto.
 */
@Data
public class ForwardSettingOfPublicHolidayCommandDto implements ForwardSettingOfPublicHolidayGetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The is transfer when public hd is minus. */
	private int isTransferWhenPublicHdIsMinus;
	
	/** The carry over deadline. */
	private int carryOverDeadline;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidayGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidayGetMemento#getIsTransferWhenPublicHdIsMinus()
	 */
	@Override
	public boolean getIsTransferWhenPublicHdIsMinus() {
		if (this.isTransferWhenPublicHdIsMinus == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidayGetMemento#getCarryOverDeadline()
	 */
	@Override
	public PublicHolidayCarryOverDeadline getCarryOverDeadline() {
		return PublicHolidayCarryOverDeadline.valueOf(this.carryOverDeadline);
	}

}
