package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.primitive.Memo;

public class SpecialHolidayHelper {
	public static SpecialHoliday createSpecialHolidayTest(NotUseAtr autoGrant, NotUseAtr continuousAcquisition) {
		return new SpecialHoliday("000000000008-0006", 
				new SpecialHolidayCode(1), 
				new SpecialHolidayName("Name"), 
				new GrantRegular(), 
				new SpecialLeaveRestriction(), 
				new TargetItem(), 
				autoGrant, 
				continuousAcquisition, 
				new Memo("Memo"));
	}
	
	public static SpecialHoliday createSpecialHolidayTest(SpecialHolidayCode specialHolidayCode) {
		return new SpecialHoliday("000000000008-0006", 
				specialHolidayCode, 
				new SpecialHolidayName("Name"), 
				new GrantRegular(), 
				new SpecialLeaveRestriction(), 
				new TargetItem(), 
				NotUseAtr.NOT_USE, 
				NotUseAtr.NOT_USE, 
				new Memo("Memo"));
	}
}
