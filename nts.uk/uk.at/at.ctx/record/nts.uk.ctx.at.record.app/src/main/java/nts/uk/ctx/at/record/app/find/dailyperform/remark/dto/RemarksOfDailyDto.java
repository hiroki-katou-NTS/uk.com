package nts.uk.ctx.at.record.app.find.dailyperform.remark.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
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

	@AttendanceItemValue(type = ValueType.TEXT)
	@AttendanceItemLayout(jpPropertyName = REMARK, layout = LAYOUT_A)
	private String remark;
	
	private int no;
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public RemarksOfDailyAttd toDomain(String employeeId, GeneralDate date) {
		RemarksOfDailyPerform domain = new RemarksOfDailyPerform(employeeId, date, new RecordRemarks(remark), no);
		return domain.getRemarks();
	}

	public static RemarksOfDailyDto getDto(RemarksOfDailyPerform x) {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		if(x != null){
			dto.setEmployeeId(x.getEmployeeId());
			dto.setYmd(x.getYmd());
			dto.setRemark(x.getRemarks().getRemarks().v());
			dto.setNo(x.getRemarks().getRemarkNo());
			dto.exsistData();
		}
		return dto;
	}
	public static RemarksOfDailyDto getDto(String employeeID,GeneralDate ymd,RemarksOfDailyAttd x) {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		if(x != null){
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.setRemark(x.getRemarks().v());
			dto.setNo(x.getRemarkNo());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public RemarksOfDailyDto clone() {
		RemarksOfDailyDto dto = new RemarksOfDailyDto();
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		dto.setRemark(remark);
		dto.setNo(no);
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}
}
