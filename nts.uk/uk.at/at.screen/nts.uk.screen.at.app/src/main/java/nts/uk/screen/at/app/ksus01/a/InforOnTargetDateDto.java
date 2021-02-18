package nts.uk.screen.at.app.ksus01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforOnTargetDateDto {

	private List<String> businessNames;
	
	private List<WorkInforAndTimeZoneByShiftMasterDto> listWorkInforAndTimeZone;
	
	private String memo;
	
	private List<AttendanceDto> listAttendaceDto;
}
