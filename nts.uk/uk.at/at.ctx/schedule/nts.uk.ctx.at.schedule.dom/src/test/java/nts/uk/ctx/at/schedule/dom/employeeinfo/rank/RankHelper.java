package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public class RankHelper {

	public static class Priority {

		public static List<RankCode> DUMMY = new ArrayList<RankCode>(
				Arrays.asList(
						new RankCode("01"), 
						new RankCode("02"), 
						new RankCode("03")));
	}
}
