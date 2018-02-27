/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
// 公休日数の繰越設定
public class ForwardSettingOfPublicHoliday extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The is transfer when public hd is minus. */
	// 公休日数がマイナス時に繰越する
	private boolean isTransferWhenPublicHdIsMinus;
	
	/** The carry over deadline. */
	// 繰越期限
	private PublicHolidayCarryOverDeadline carryOverDeadline;
	
	public ForwardSettingOfPublicHoliday(ForwardSettingOfPublicHolidayGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.isTransferWhenPublicHdIsMinus = memento.getIsTransferWhenPublicHdIsMinus();
		this.carryOverDeadline = memento.getCarryOverDeadline();
	}
	
	public void saveToMemento(ForwardSettingOfPublicHolidaySetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setIsTransferWhenPublicHdIsMinus(this.isTransferWhenPublicHdIsMinus);
		memento.setCarryOverDeadline(this.carryOverDeadline);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForwardSettingOfPublicHoliday other = (ForwardSettingOfPublicHoliday) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		return true;
	}
	
	
}
