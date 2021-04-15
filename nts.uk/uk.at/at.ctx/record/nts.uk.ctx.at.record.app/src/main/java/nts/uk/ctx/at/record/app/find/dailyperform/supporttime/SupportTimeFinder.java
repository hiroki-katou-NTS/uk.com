/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyperform.supporttime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto.OuenWorkTimeSheetOfDailyAttendanceDto;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

/**
 * @author laitv
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SupportTimeFinder extends FinderFacade{
	
	@Inject
	private OuenWorkTimeSheetOfDailyRepo repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> finds(String employeeId, GeneralDate baseDate) {
		
		OuenWorkTimeSheetOfDaily ouenWorkTimeSheetOfDaily = repo.find(employeeId, baseDate);
		if(ouenWorkTimeSheetOfDaily == null || ouenWorkTimeSheetOfDaily.getOuenTimeSheet().isEmpty())
			return null;
		
		return (List<T>)  ouenWorkTimeSheetOfDaily.getOuenTimeSheet()
				.stream()
				.map(c -> OuenWorkTimeSheetOfDailyAttendanceDto.from(employeeId, baseDate, c))
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeIds, DatePeriod period) {
		List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailys = employeeIds.stream().map(c -> this.repo.find(c, period))
				.flatMap(List::stream)
				.collect(Collectors.toList());
		
		List<OuenWorkTimeSheetOfDailyAttendanceDto> rs =  ouenWorkTimeSheetOfDailys
				.stream()
				.map(d -> d.getOuenTimeSheet().stream().map(e -> OuenWorkTimeSheetOfDailyAttendanceDto.from(d.getEmpId(), d.getYmd(), e)).collect(Collectors.toList()))
				.flatMap(List::stream)
				.collect(Collectors.toList());
		
		return (List<T>) rs;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Map<String, List<GeneralDate>> param) {
		return (List<T>) param.keySet().stream()
						.map(c -> param.get(c).stream().map(d -> finds(c, d))
													.flatMap(List::stream)
													.filter(d -> d.isHaveData())
													.collect(Collectors.toList()))
						.flatMap(List::stream)
						.collect(Collectors.toList());
	}

	@Override
	public Object getDomain(String employeeId, GeneralDate baseDate) {
		val domain = this.repo.find(employeeId, baseDate);
		return domain;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OuenWorkTimeSheetOfDailyAttendanceDto find(String employeeId, GeneralDate baseDate) {
		
		OuenWorkTimeSheetOfDaily ouenWorkTimeSheetOfDaily = repo.find(employeeId, baseDate);
		if(ouenWorkTimeSheetOfDaily == null || ouenWorkTimeSheetOfDaily.getOuenTimeSheet().isEmpty())
			return null;
		OuenWorkTimeSheetOfDailyAttendanceDto rs = OuenWorkTimeSheetOfDailyAttendanceDto.from(employeeId, baseDate, ouenWorkTimeSheetOfDaily.getOuenTimeSheet().get(0));
		return rs;
	}
	
}
