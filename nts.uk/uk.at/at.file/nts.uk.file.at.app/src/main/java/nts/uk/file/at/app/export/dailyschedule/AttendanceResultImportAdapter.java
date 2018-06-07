package nts.uk.file.at.app.export.dailyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AttendanceResultImportAdapter {
	@Inject
	private DailyRecordWorkFinder fullFinder;
	
	public AttendanceResultImport getValueOf(String employeeId, GeneralDate workingDate, List<Integer> itemIds) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return getAndConvert(employeeId, workingDate, itemDtos, itemIds);
	}
	
	private AttendanceResultImport getAndConvert(String employeeId, GeneralDate workingDate, DailyRecordDto data, List<Integer> itemIds){
		return AttendanceResultImport.builder().employeeId(employeeId).workingDate(workingDate)
				.attendanceItems(AttendanceItemUtil.toItemValues(data, itemIds).stream()
						.map(c -> new AttendanceItemValueImport(c.getValueType().value, c.itemId(), c.value()))
						.collect(Collectors.toList())).build();
	}
	
	public List<AttendanceResultImport> getValueOf(List<String> employeeId, DatePeriod workingDate, List<Integer> itemIds) {
		if(employeeId == null || employeeId.isEmpty() || workingDate == null){
			return new ArrayList<>();
		}
		return this.fullFinder.find(employeeId, workingDate).stream()
				.map(c -> getAndConvert(c.employeeId(), c.workingDate(),(DailyRecordDto)  c, itemIds))
				.collect(Collectors.toList());
	}
	
	public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
	    return from.stream().map(func).collect(Collectors.toList());
	}
}
