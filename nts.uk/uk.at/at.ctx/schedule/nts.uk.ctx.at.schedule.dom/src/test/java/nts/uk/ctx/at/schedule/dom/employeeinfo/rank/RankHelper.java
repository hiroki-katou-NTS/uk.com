package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public class RankHelper {
	
	public static class Priority{
		public static final List<RankCode> DUMMY = new ArrayList<RankCode>(Arrays.asList(new RankCode("001"), new RankCode("002"), new RankCode("003")));
	}
	
	public static RankPriority getRankPriorityBasic() {
		List<RankCode> listRankCd = new ArrayList<>();
		listRankCd.add(new RankCode("001"));
		listRankCd.add(new RankCode("002"));
		listRankCd.add(new RankCode("003"));
		listRankCd.add(new RankCode("004"));
		return new RankPriority("0001", listRankCd);
	}

	public static RankPriority getListRankCodeEmpty() {
		return new RankPriority("0001", Collections.emptyList());
	}

	public static RankPriority getListRankCodeNotDistinct() {
		List<RankCode> listRankCdNotDistinct = new ArrayList<>();
		listRankCdNotDistinct.add(new RankCode("001"));
		listRankCdNotDistinct.add(new RankCode("001"));
		listRankCdNotDistinct.add(new RankCode("002"));
		listRankCdNotDistinct.add(new RankCode("003"));
		return new RankPriority("0001", listRankCdNotDistinct);
	}

	public static RankPriority getListRankCode1Item() {
		List<RankCode> listRankCd = new ArrayList<>(Arrays.asList(new RankCode("001")));
		return new RankPriority("0001", listRankCd);
	}
	
	public static boolean checkExist(boolean param){
		return param;
	}
	
	public static RankPriority setRankPriorityNull(){
		return null;
	}

}
