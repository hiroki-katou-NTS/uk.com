package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

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
	private WorkLocationCd workLocationCd;
	private StampReason stampReason;
	private GeneralDate date;

	public static StampItem createFromJavaType(String companyID, String cardNumber, int attendanceTime,
			int stampCombinationAtr, String workTimeCd, int stampMethod, int stampAtr, String workLocationCd,
			int stampReason, GeneralDate date) {
		return new StampItem(companyID, new CardNumber(cardNumber), 
				new AttendanceTime(attendanceTime),
				EnumAdaptor.valueOf(stampCombinationAtr, StampCombinationAtr.class), 
				new WorkTimeCd(workTimeCd),
				EnumAdaptor.valueOf(stampMethod, StampMethod.class), 
				EnumAdaptor.valueOf(stampAtr, StampAtr.class),
				new WorkLocationCd(workLocationCd), 
				EnumAdaptor.valueOf(stampReason, StampReason.class), 
				date);
	}
}
