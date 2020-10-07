package nts.uk.screen.at.app.query.ksu.ksu002.a.input;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetDateInfoDuringThePeriodInput {

	public List<String> sids;
	public GeneralDate generalDate;
	
}
