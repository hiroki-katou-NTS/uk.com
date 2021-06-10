package nts.uk.screen.at.app.command.kdp.kdp003.m;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectingWorkplacesForStampInputParam {

	public String sid;
	public List<String> workPlaceIds;
	
}
