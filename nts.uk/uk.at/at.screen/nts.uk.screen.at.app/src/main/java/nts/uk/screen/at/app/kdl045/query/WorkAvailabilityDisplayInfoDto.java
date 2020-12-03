package nts.uk.screen.at.app.kdl045.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkAvailabilityDisplayInfoDto {

	private int assignmentMethod;
	
	private List<String> nameList;
	
	private List<TimeSpanForCalcDto> timeZoneList;
	
}
