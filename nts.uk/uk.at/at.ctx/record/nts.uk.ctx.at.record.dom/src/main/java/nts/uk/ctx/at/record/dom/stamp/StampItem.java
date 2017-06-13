package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationName;
//import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeCode;


@AllArgsConstructor
@Getter
public class StampItem extends AggregateRoot {
	private String companyId;
	private CardNumber cardNumber;
	private AttendanceTime attendanceTime;
	private StampCombinationAtr stampCombinationAtr;
	private WorkTimeCd workTimeCd;
	private StampMethod stampMethod;
	private StampAtr stampAtr;
	private WorkLocationCD workLocationCd;
	private WorkLocationName workLocationName;
	private StampReason stampReason;
	private GeneralDate date;
	private String personId;

	public static StampItem createFromJavaType(String companyID, String cardNumber, int attendanceTime,
			int stampCombinationAtr, String workTimeCd, int stampMethod, int stampAtr, String workLocationCd,String workLocationName,
			int stampReason, GeneralDate date,String personId ) {
		return new StampItem(companyID, new CardNumber(cardNumber), 
				new AttendanceTime(attendanceTime),
				EnumAdaptor.valueOf(stampCombinationAtr, StampCombinationAtr.class), 
				new WorkTimeCd(workTimeCd),
				EnumAdaptor.valueOf(stampMethod, StampMethod.class), 
				EnumAdaptor.valueOf(stampAtr, StampAtr.class),
				new WorkLocationCD(workLocationCd), 
				new WorkLocationName(workLocationName),
				EnumAdaptor.valueOf(stampReason, StampReason.class), 
				date,
				personId);
	}
}
