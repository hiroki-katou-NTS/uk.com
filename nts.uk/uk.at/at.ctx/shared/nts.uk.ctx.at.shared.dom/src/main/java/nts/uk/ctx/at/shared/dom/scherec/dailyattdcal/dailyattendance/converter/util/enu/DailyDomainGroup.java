package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;

public enum DailyDomainGroup {

	WORK_INFO(1, ItemConst.DAILY_WORK_INFO_NAME),
	AFFILIATION_INFO(2, ItemConst.DAILY_AFFILIATION_INFO_NAME),
	CALCULATION_ATTR(3, ItemConst.DAILY_CALCULATION_ATTR_NAME),
	BUSINESS_TYPE(4, ItemConst.DAILY_BUSINESS_TYPE_NAME),
	OUTING_TIME(5, ItemConst.DAILY_OUTING_TIME_NAME),
	BREAK_TIME(6, ItemConst.DAILY_BREAK_TIME_NAME),
	ATTENDANCE_TIME(7, ItemConst.DAILY_ATTENDANCE_TIME_NAME),
	ATTENDANCE_TIME_BY_WORK(8, ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME),
	ATTENDACE_LEAVE(9, ItemConst.DAILY_ATTENDACE_LEAVE_NAME),
	SHORT_TIME(10, ItemConst.DAILY_SHORT_TIME_NAME),
	SPECIFIC_DATE_ATTR(11, ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME),
	ATTENDANCE_LEAVE_GATE(12, ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME),
	OPTIONAL_ITEM(13, ItemConst.DAILY_OPTIONAL_ITEM_NAME),
	EDIT_STATE(14, ItemConst.DAILY_EDIT_STATE_NAME),
	TEMPORARY_TIME(15, ItemConst.DAILY_TEMPORARY_TIME_NAME),
	PC_LOG_INFO(16, ItemConst.DAILY_PC_LOG_INFO_NAME),
	REMARKS(17, ItemConst.DAILY_REMARKS_NAME);
	
	public final int value;
	public final String name;
	
	private DailyDomainGroup (int value, String description){
		this.value = value;
		this.name = description;
	}
}
