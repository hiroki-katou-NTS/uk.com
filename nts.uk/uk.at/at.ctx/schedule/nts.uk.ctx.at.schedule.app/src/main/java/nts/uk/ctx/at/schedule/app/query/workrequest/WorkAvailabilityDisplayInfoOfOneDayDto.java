package nts.uk.ctx.at.schedule.app.query.workrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * List<一日分の勤務希望の表示情報>Dto
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkAvailabilityDisplayInfoOfOneDayDto {
	private String employeeId;
	
	private GeneralDate availabilityDate;
	
	private String memo;
	
	private WorkAvailabilityDisplayInfoScheduleDto displayInfo;

	public WorkAvailabilityDisplayInfoOfOneDayDto(String employeeId, GeneralDate availabilityDate, String memo,
			WorkAvailabilityDisplayInfoScheduleDto displayInfo) {
		super();
		this.employeeId = employeeId;
		this.availabilityDate = availabilityDate;
		this.memo = memo;
		this.displayInfo = displayInfo;
	}
	
}
