package nts.uk.ctx.at.record.app.find.dailyperform.remark.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RecordRemarks;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_REMARKS_NAME)
public class RemarksOfDailyDto extends AttendanceItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = FAKED, listMaxLength = 5, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RemarkDto> remarks = new ArrayList<>();
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public List<RemarksOfDailyAttd> toDomain(String employeeId, GeneralDate date) {
		if (this.isHaveData()) {
			return remarks.stream()
					.map(c -> new RemarksOfDailyAttd(new RecordRemarks(c.getRemark()), c.getNo()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public static RemarksOfDailyDto getDto(List<RemarksOfDailyPerform> domain) {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		if(domain != null && !domain.isEmpty()){
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYmd(domain.get(0).getYmd());
			dto.setRemarks(domain.stream().map(c -> new RemarkDto(c.getRemarks().getRemarks().v(), c.getRemarks().getRemarkNo()))
											.collect(Collectors.toList()));
			dto.exsistData();
		}
		return dto;
	}
	
	public static RemarksOfDailyDto getDto(String employeeID, GeneralDate ymd, List<RemarksOfDailyAttd> domain) {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		if(domain != null && !domain.isEmpty()){
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.setRemarks(domain.stream()
					.map(c -> new RemarkDto(c.getRemarks().v(), c.getRemarkNo()))
					.collect(Collectors.toList()));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public RemarksOfDailyDto clone() {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		dto.setRemarks(remarks.stream().map(c -> new RemarkDto(c.getRemark(), c.getNo())).collect(Collectors.toList()));
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (FAKED.equals(path)) {
			return new RemarkDto();
		}
		return null;
	}

	@Override
	public int size(String path) {
		return 5;
	}

	@Override
	public PropType typeOf(String path) {
		if (FAKED.equals(path)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}

	@Override
	public boolean isRoot() { return true; }

	@Override
	public String rootName() { return DAILY_REMARKS_NAME; }
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (FAKED.equals(path)) {
			return (List<T>) this.remarks;
		}
		return super.gets(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (FAKED.equals(path)) {
			this.remarks = (List<RemarkDto>) value;
		}
	}
}
