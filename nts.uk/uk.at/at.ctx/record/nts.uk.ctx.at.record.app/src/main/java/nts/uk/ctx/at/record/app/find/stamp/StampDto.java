package nts.uk.ctx.at.record.app.find.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.stamp.StampItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StampDto {
	private String cardNumber;
	private int attendanceTime;
	private int stampCombinationAtr;
	private String stampCombinationName;
	private String siftCd;
	private int stampMethod;
	private String stampMethodName;
	private int stampAtr;
	private String stampAtrName;
	private String workLocationCd;
	private String workLocationName;
	private int stampReason;
	private String stampReasonName;
	private GeneralDateTime date;
	private String employeeId;
	private String employeeCode;
	private String pName;
	
	//Convert from Stamp domain to stamp dto, when exist data and null.
	public static StampDto fromDomain(StampItem domain, EmployeeRecordImport empInfor) {
		return new StampDto(domain.getCardNumber() == null ? "" : domain.getCardNumber().v(), 
				domain.getAttendanceTime() == null? -1 : domain.getAttendanceTime().v(), 
				domain.getStampCombinationAtr() == null? -1 : domain.getStampCombinationAtr().value,
				domain.getStampCombinationAtr() == null? "" : domain.getStampCombinationAtr().name,
				domain.getSiftCd() == null? "" : domain.getSiftCd().v(), 
				domain.getStampMethod() == null? -1 : domain.getStampMethod().value,
				domain.getStampMethod() == null? "" : domain.getStampMethod().name,
				domain.getStampAtr() == null? -1 : domain.getStampAtr().value,
				domain.getStampAtr() == null? "" : domain.getStampAtr().name,
				domain.getWorkLocationCd() == null? "" : domain.getWorkLocationCd().v(), 
				domain.getWorkLocationName() == null? "" : domain.getWorkLocationName().v(),
				domain.getGoOutReason() == null? -1 : domain.getGoOutReason().value,
				domain.getGoOutReason() == null? "" : domain.getGoOutReason().nameId,
				domain.getDate() == null? null : domain.getDate(),
				domain.getEmployeeId() == null? "" : domain.getEmployeeId(),
				empInfor.getEmployeeCode(),
				empInfor.getPname());
	}
}
