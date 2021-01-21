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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class AttendanceResultImportAdapter.
 * @author HoangNDH
 */
@Stateless
public class AttendanceResultImportAdapter {
	
	/** The full finder. */
	@Inject
	private DailyRecordWorkFinder fullFinder;
	
	/**
	 * Gets the value of attendance result.
	 *
	 * @param employeeId the employee id
	 * @param workingDate the working date
	 * @param itemIds the item ids
	 * @return the value of
	 */
	public AttendanceResultImport getValueOf(String employeeId, GeneralDate workingDate, List<Integer> itemIds) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return getAndConvert(employeeId, workingDate, itemDtos, itemIds);
	}
	
	/**
	 * Gets and convert.
	 *
	 * @param employeeId the employee id
	 * @param workingDate the working date
	 * @param data the data
	 * @param itemIds the item ids
	 * @return the and convert
	 */
	private AttendanceResultImport getAndConvert(String employeeId, GeneralDate workingDate, DailyRecordDto data, List<Integer> itemIds){
		return AttendanceResultImport.builder().employeeId(employeeId).workingDate(workingDate)
				.attendanceItems(AttendanceItemUtil.toItemValues(data, itemIds).stream()
						.map(c -> new AttendanceItemValueImport(c.getValueType().value, c.itemId(), c.value()))
						.collect(Collectors.toList())).build();
	}
	
	/**
	 * Gets the value of attendance result import by list of employee and attendance id.
	 *
	 * @param employeeId the employee id
	 * @param workingDate the working date
	 * @param itemIds the item ids
	 * @return the value of
	 */
	public List<AttendanceResultImport> getValueOf(List<String> employeeId, DatePeriod workingDate, List<Integer> itemIds) {
		if(employeeId == null || employeeId.isEmpty() || workingDate == null){
			return new ArrayList<>();
		}
		return this.fullFinder.find(employeeId, workingDate).stream()
				.map(c -> getAndConvert(c.employeeId(), c.workingDate(),(DailyRecordDto)  c, itemIds))
				.collect(Collectors.toList());
	}
	
	/**
	 * Convert list.
	 *
	 * @param <T> the generic type
	 * @param <U> the generic type
	 * @param from the from
	 * @param func the func
	 * @return the list
	 */
	public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
	    return from.stream().map(func).collect(Collectors.toList());
	}
}
