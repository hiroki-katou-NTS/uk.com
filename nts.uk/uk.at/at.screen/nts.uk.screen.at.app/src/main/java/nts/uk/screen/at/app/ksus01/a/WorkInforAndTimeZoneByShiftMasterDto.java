package nts.uk.screen.at.app.ksus01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkInforAndTimeZoneByShiftMasterDto {

	private String wishName;
	
	private List<TimeZoneDto> timezones;
	
	private Integer workStyle;
	
	private String color;
	
}
