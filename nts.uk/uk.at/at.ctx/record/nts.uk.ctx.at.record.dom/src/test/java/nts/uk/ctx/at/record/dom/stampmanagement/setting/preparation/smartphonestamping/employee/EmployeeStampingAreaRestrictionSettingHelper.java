package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

public class EmployeeStampingAreaRestrictionSettingHelper {
	public static final String CID = "000000000004-0004";
	public static final String SID = "000000000001";
	public static final String CONTRACTCD = "000000000004"; 
	
	
	public static EmployeeStampingAreaRestrictionSetting getStampDefault() {
		String employeeId  = "dummy";
		NotUseAtr notUseAtr = NotUseAtr.USE;
		StampingAreaLimit areaLimit = StampingAreaLimit.NO_AREA_RESTRICTION;
		
		StampingAreaRestriction stampingAreaRestriction = new StampingAreaRestriction(notUseAtr,areaLimit);
		return new EmployeeStampingAreaRestrictionSetting(employeeId ,stampingAreaRestriction);
	}
	
	public static StampingAreaRestriction  createStamp () {
		NotUseAtr notUseAtr = NotUseAtr.USE;
		StampingAreaLimit areaLimit = StampingAreaLimit.NO_AREA_RESTRICTION;
		StampingAreaRestriction areaRestriction = new StampingAreaRestriction(notUseAtr,areaLimit);
		return areaRestriction;
	}
} 







