package nts.uk.ctx.at.function.app.find.alarm.extraprocessstatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;

@Getter
@Setter
@NoArgsConstructor
public class AlarmListExtraProcessStatusDto {

	/**会社ID */
	private String companyID;
	/** 開始年月日*/
	private GeneralDate startDate;
	/** 開始時刻 */
	private int startTime;
	/**実行社員*/
	private String employeeID;
	/**終了年月日*/
	private GeneralDate endDate;
	/**終了時刻*/
	private Integer endTime;
	public AlarmListExtraProcessStatusDto(String companyID, GeneralDate startDate, int startTime, String employeeID,
			GeneralDate endDate, Integer endTime) {
		super();
		this.companyID = companyID;
		this.startDate = startDate;
		this.startTime = startTime;
		this.employeeID = employeeID;
		this.endDate = endDate;
		this.endTime = endTime;
	}
	
	public static AlarmListExtraProcessStatusDto fromDomain( AlarmListExtraProcessStatus domain) {
		return new AlarmListExtraProcessStatusDto(
				domain.getCompanyID(),
				domain.getStartDate(),
				domain.getStartTime(),
				domain.getEmployeeID().orElse(null),
				domain.getEndDate().orElse(null),
				domain.getEndTime().orElse(null)
				);
	}
}
