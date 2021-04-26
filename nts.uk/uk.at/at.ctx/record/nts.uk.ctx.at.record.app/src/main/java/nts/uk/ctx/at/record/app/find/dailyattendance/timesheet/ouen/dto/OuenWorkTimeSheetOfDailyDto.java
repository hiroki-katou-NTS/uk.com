package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_SUPPORT_TIMESHEET_NAME)
public class OuenWorkTimeSheetOfDailyDto extends AttendanceItemCommon {
	
	private static final long serialVersionUID = 1L;
	
	private String empId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = LAYOUT_U, jpPropertyName = FAKED, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OuenWorkTimeSheetOfDailyAttendanceDto> ouenTimeSheet;
	
	
	@Override
	public int size(String path) {
		if (path.equals(DAILY_SUPPORT_TIMESHEET_NAME)) {
			return 20;
		} else {
			return super.size(path);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(DAILY_SUPPORT_TIMESHEET_NAME)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}
	
	@Override
	public boolean isRoot() { return true; }

	@Override
	public String rootName() { return DAILY_SUPPORT_TIMESHEET_NAME; }
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (FAKED.equals(path)) {
			return (List<T>) this.ouenTimeSheet;
		}
		return super.gets(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (FAKED.equals(path)) {
			this.ouenTimeSheet = (List<OuenWorkTimeSheetOfDailyAttendanceDto>) value;
		}
	}
	
	@Override
	public String employeeId() {
		return this.empId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public List<OuenWorkTimeSheetOfDailyAttendance> toDomain(String employeeId, GeneralDate date) {
		if (this.isHaveData()) {
			return ouenTimeSheet.stream()
					.map(c -> c.toDomain(employeeId, date))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	public static OuenWorkTimeSheetOfDailyDto getDto(String employeeId, GeneralDate date, OuenWorkTimeSheetOfDaily domainDaily) {
		OuenWorkTimeSheetOfDailyDto dto = new OuenWorkTimeSheetOfDailyDto();
		if (domainDaily != null) {
			List<OuenWorkTimeSheetOfDailyAttendance> domain = domainDaily.getOuenTimeSheet();
			if (!domain.isEmpty()) {
				dto.setEmpId(employeeId);
				dto.setYmd(date);
				dto.setOuenTimeSheet(domain.stream().map(c -> OuenWorkTimeSheetOfDailyAttendanceDto.from(employeeId, date, c)).collect(Collectors.toList()));
				dto.exsistData();
			}
		}
		return dto;
	}
	
	public static OuenWorkTimeSheetOfDailyDto getDto(OuenWorkTimeSheetOfDaily domainDaily) {
		OuenWorkTimeSheetOfDailyDto dto = new OuenWorkTimeSheetOfDailyDto();
		if (domainDaily != null) {
			List<OuenWorkTimeSheetOfDailyAttendance> domain = domainDaily.getOuenTimeSheet();
			if (!domain.isEmpty()) {
				dto.setEmpId(domainDaily.getEmpId());
				dto.setYmd(domainDaily.getYmd());
				dto.setOuenTimeSheet(domain.stream().map(c -> OuenWorkTimeSheetOfDailyAttendanceDto.from(domainDaily.getEmpId(), domainDaily.getYmd(), c)).collect(Collectors.toList()));
				dto.exsistData();
			}
		}
		return dto;
	}
	
	@Override
	public OuenWorkTimeSheetOfDailyDto clone() {
		OuenWorkTimeSheetOfDailyDto dto = new OuenWorkTimeSheetOfDailyDto();
		dto.setEmpId(employeeId());
		dto.setYmd(workingDate());
		dto.setOuenTimeSheet(ouenTimeSheet.stream().map(c -> new OuenWorkTimeSheetOfDailyAttendanceDto(c.getEmployeeId(), c.getDate(), c.getNo(), 
				new WorkContentDto(
						new WorkplaceOfWorkEachOuenDto(c.getWorkContent().getWorkplace().getWorkplaceId(), c.getWorkContent().getWorkplace().getWorkLocationCD()), 
						new WorkGroupDto(c.getWorkContent().getWork().getWorkCD1(), c.getWorkContent().getWork().getWorkCD1(), 
								c.getWorkContent().getWork().getWorkCD2(), c.getWorkContent().getWork().getWorkCD3(), c.getWorkContent().getWork().getWorkCD4()), 
						c.getWorkContent().getWorkRemarks()),
				new TimeSheetOfAttendanceEachOuenSheetDto(c.getTimeSheet().getNo(), new WorkTimeInformationDto(), new WorkTimeInformationDto()))).collect(Collectors.toList()));
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}

}
