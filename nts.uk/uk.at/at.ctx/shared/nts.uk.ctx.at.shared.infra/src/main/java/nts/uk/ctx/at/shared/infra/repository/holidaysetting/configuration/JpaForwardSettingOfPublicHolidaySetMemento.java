package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.ForwardSettingOfPublicHolidaySetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayCarryOverDeadline;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdpubForwardSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaForwardSettingOfPublicHolidaySetMemento.
 */
public class JpaForwardSettingOfPublicHolidaySetMemento implements ForwardSettingOfPublicHolidaySetMemento{
	
	/** The Constant TRUE_VALUE. */
	private static final int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private static final int FALSE_VALUE = 0;
	
	/** The kshmt forward set of public hd. */
	private KshmtHdpubForwardSet kshmtHdpubForwardSet;
	
	/**
	 * Instantiates a new jpa forward setting of public holiday set memento.
	 *
	 * @param entity the entity
	 */
	public JpaForwardSettingOfPublicHolidaySetMemento(KshmtHdpubForwardSet entity){
		if(entity.getCid() == null){
			entity.setCid(AppContexts.user().companyId());
		}
		this.kshmtHdpubForwardSet = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.kshmtHdpubForwardSet.setCid(companyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setIsTransferWhenPublicHdIsMinus(boolean)
	 */
	@Override
	public void setIsTransferWhenPublicHdIsMinus(boolean isTransferWhenPublicHdIsMinus) {
		if(isTransferWhenPublicHdIsMinus){
			this.kshmtHdpubForwardSet.setIsPublicHdMinus(TRUE_VALUE);
		} else {
			this.kshmtHdpubForwardSet.setIsPublicHdMinus(FALSE_VALUE);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidaySetMemento#setCarryOverDeadline(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayCarryOverDeadline)
	 */
	@Override
	public void setCarryOverDeadline(PublicHolidayCarryOverDeadline carryOverDeadline) {
		this.kshmtHdpubForwardSet.setCarryOverDeadline(carryOverDeadline.value);
	}
	
}
