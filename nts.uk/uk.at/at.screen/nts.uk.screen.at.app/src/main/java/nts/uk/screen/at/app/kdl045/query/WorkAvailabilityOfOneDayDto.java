package nts.uk.screen.at.app.kdl045.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 一日分の勤務希望の表示情報 Dto
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkAvailabilityOfOneDayDto {
	/**
	 * 社員ID
	 */
	private String employeeId;
		
	/**
	 * 希望日
	 */
	private GeneralDate workAvailabilityDate;
	
	/**
	 * メモ
	 */
	private String memo;
	
	/**
	 * 勤務希望
	 */
	private WorkAvailabilityDisplayInfoDto workAvaiByHolidayDto;

	public WorkAvailabilityOfOneDayDto(String employeeId, GeneralDate workAvailabilityDate, String memo,
			WorkAvailabilityDisplayInfoDto workAvaiByHolidayDto) {
		super();
		this.employeeId = employeeId;
		this.workAvailabilityDate = workAvailabilityDate;
		this.memo = memo;
		this.workAvaiByHolidayDto = workAvaiByHolidayDto;
	}
	
}
