package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItemDomainServiceDto;

public interface DailyAttendanceItemNameDomainServiceRec {
	
	/*
	 * Set name of dailyAttendanceItem
	 * 勤怠項目に対応する名称を生成する
	 */
	List<DailyAttendanceItemDomainServiceDto> getNameOfDailyAttendanceItem(List<Integer> dailyAttendanceItemIds);
}
