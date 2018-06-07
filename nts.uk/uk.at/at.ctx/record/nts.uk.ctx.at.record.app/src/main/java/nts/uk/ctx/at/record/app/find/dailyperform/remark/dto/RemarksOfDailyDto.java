package nts.uk.ctx.at.record.app.find.dailyperform.remark.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.remarks.RecordRemarks;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceLayoutConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

@Data
@AttendanceItemRoot(rootName = AttendanceLayoutConst.DAILY_REMARKS_NAME)
public class RemarksOfDailyDto extends AttendanceItemCommon {
	
	private String employeeId;
	
	private GeneralDate ymd;

	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "備考", layout = "A")
	private String remark;
	
	private int remarkNo;
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public RemarksOfDailyPerform toDomain(String employeeId, GeneralDate date) {
		return new RemarksOfDailyPerform(employeeId, date, new RecordRemarks(remark), remarkNo);
	}

	public static RemarksOfDailyDto getDto(RemarksOfDailyPerform x) {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		if(x != null){
			dto.setEmployeeId(x.getEmployeeId());
			dto.setYmd(x.getYmd());
			dto.setRemark(x.getRemarks().v());
			dto.setRemarkNo(x.getRemarkNo());
			dto.exsistData();
		}
		return dto;
	}
}
