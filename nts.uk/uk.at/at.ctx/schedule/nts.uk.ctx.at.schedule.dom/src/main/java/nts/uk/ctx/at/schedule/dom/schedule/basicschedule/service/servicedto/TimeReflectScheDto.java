package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class TimeReflectScheDto {
	/**	社員ID */
	private String employeeId;
	/**	年月日 */
	private GeneralDate dateInfo;
	/**	開始時刻 */
	private Integer startTime;
	/** 終了時刻 */
	private Integer endTime;
	/**	枠番号 */
	private Integer frameNumber;
	
	private boolean isUpdateStart;
	
	private boolean isUpdateEnd;

}
