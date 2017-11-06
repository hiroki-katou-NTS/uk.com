package nts.uk.ctx.at.schedule.dom.shift.rank;

import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface RankRepository {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<Rank> getListRank(String companyId);
}
