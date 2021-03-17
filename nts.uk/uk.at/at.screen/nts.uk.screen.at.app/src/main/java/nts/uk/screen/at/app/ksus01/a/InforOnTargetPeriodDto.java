package nts.uk.screen.at.app.ksus01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforOnTargetPeriodDto {

	private List<WorkScheduleDto> listWorkSchedule;
	
	private List<DesiredSubmissionStatusByDate> listDesiredSubmissionStatusByDate;
	
	private List<PublicHolidayDto> listPublicHolidayDto;
}
