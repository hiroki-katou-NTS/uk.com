package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_PC_LOG_INFO_NAME)
public class PCLogOnInforOfDailyPerformDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_PC_LOG_INFO_NAME; }

	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;

	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = INFO, listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<LogonInfoDto> logonTime;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
	
	public static PCLogOnInforOfDailyPerformDto from(PCLogOnInfoOfDaily domain){
		PCLogOnInforOfDailyPerformDto dto = new PCLogOnInforOfDailyPerformDto();
		if (domain != null) {
			dto.setLogonTime(ConvertHelper.mapTo(domain.getTimeZone().getLogOnInfo(),
					(c) -> new LogonInfoDto(
								c.getWorkNo().v(),
								c.getLogOn().isPresent() ? c.getLogOn().get().valueAsMinutes() : null,
								c.getLogOff().isPresent() ? c.getLogOff().get().valueAsMinutes() : null
					)));
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.exsistData();
		}
		return dto;
	}
	
	public static PCLogOnInforOfDailyPerformDto from(String employeeID,GeneralDate ymd,PCLogOnInfoOfDailyAttd domain){
		PCLogOnInforOfDailyPerformDto dto = new PCLogOnInforOfDailyPerformDto();
		if (domain != null) {
			dto.setLogonTime(ConvertHelper.mapTo(domain.getLogOnInfo(),
					(c) -> new LogonInfoDto(
								c.getWorkNo().v(),
								c.getLogOn().isPresent() ? c.getLogOn().get().valueAsMinutes() : null,
								c.getLogOff().isPresent() ? c.getLogOff().get().valueAsMinutes() : null
					)));
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public PCLogOnInforOfDailyPerformDto clone(){
		PCLogOnInforOfDailyPerformDto dto = new PCLogOnInforOfDailyPerformDto();
		dto.setLogonTime(logonTime == null ? null : logonTime.stream().map(t -> t.clone()).collect(Collectors.toList()));
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		if (isHaveData()) {
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public PCLogOnInfoOfDailyAttd toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		PCLogOnInfoOfDaily domain = new PCLogOnInfoOfDaily(employeeId, date, ConvertHelper.mapTo(logonTime, 
							c -> new LogOnInfo(new PCLogOnNo(c.getNo()),
								toWorkStamp(c.getLogOff()), toWorkStamp(c.getLogOn()))));
		return domain.getTimeZone();
	}

	private TimeWithDayAttr toWorkStamp(Integer time){
		return time == null ? null : new TimeWithDayAttr(time);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (INFO.equals(path)) {
			return new LogonInfoDto();
		}
		return null;
	}

	@Override
	public int size(String path) {
		return 2;
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public PropType typeOf(String path) {
		if (INFO.equals(path)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (INFO.equals(path)) {
			return (List<T>) this.logonTime;
		}
		return super.gets(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (INFO.equals(path)) {
			this.logonTime = (List<LogonInfoDto>) value;
		}
	}
}
