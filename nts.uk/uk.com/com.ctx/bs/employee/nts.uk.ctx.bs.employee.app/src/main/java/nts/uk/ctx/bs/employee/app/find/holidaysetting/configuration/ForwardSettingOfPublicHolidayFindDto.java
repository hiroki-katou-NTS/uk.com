package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayCarryOverDeadline;

/**
 * The Class ForwardSettingOfPublicHolidayFindDto.
 */
@Data
public class ForwardSettingOfPublicHolidayFindDto implements ForwardSettingOfPublicHolidaySetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The is transfer when public hd is minus. */
	private int isTransferWhenPublicHdIsMinus;
	
	/** The carry over deadline. */
	private int carryOverDeadline;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		// do not code
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setIsTransferWhenPublicHdIsMinus(boolean)
	 */
	@Override
	public void setIsTransferWhenPublicHdIsMinus(boolean isTransferWhenPublicHdIsMinus) {
		if(isTransferWhenPublicHdIsMinus){
			this.isTransferWhenPublicHdIsMinus = TRUE_VALUE;
		} else {
			this.isTransferWhenPublicHdIsMinus = FALSE_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setCarryOverDeadline(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayCarryOverDeadline)
	 */
	@Override
	public void setCarryOverDeadline(PublicHolidayCarryOverDeadline carryOverDeadline) {
		this.carryOverDeadline = carryOverDeadline.value;
	}

}
