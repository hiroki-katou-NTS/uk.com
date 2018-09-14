package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import java.util.Optional;

import javax.persistence.EnumType;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;

@Value
public class AlarmListExtraProcessStatusCmd {

	/**ID*/
	private String extraProcessStatusID;
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
	
	private int status;
	
	public static AlarmListExtraProcessStatus toDomain(AlarmListExtraProcessStatusCmd command) {
		return new AlarmListExtraProcessStatus(
				command.getExtraProcessStatusID(),
				command.getCompanyID(),
				command.getStartDate(),
				command.getStartTime(),
				command.getEmployeeID(),
				command.getEndDate(),
				command.getEndTime(),
				EnumAdaptor.valueOf(command.status, ExtractionState.class)  
				);
	}
}
