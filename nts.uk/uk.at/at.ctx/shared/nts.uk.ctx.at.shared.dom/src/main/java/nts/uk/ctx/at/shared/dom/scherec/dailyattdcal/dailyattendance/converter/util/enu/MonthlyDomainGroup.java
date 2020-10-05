package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;

public enum MonthlyDomainGroup {

	AFFILIATION_INFO(1, ItemConst.MONTHLY_AFFILIATION_INFO_NAME),
	ATTENDANCE_TIME(2, ItemConst.MONTHLY_ATTENDANCE_TIME_NAME),
	OPTIONAL_ITEM(3, ItemConst.MONTHLY_OPTIONAL_ITEM_NAME),
	ANNUAL_LEAVING_REMAIN(4, ItemConst.MONTHLY_ANNUAL_LEAVING_REMAIN_NAME),
	RESERVE_LEAVING_REMAIN(5, ItemConst.MONTHLY_RESERVE_LEAVING_REMAIN_NAME);
	
	public final int value;
	public final String name;
	
	private MonthlyDomainGroup (int value, String description){
		this.value = value;
		this.name = description;
	}
}
