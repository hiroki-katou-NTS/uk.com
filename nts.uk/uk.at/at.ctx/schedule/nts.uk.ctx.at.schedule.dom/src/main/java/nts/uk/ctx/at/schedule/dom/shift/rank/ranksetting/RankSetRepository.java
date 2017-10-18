package nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting;

import java.util.List;

/**
 * 
 * @author Trung Tran
 *
 */
public interface RankSetRepository {
	/**
	 * get all list rank set
	 * 
	 * @return list rankset
	 */
	List<RankSet> getListRankSet(List<String> employeeIds);

	/**
	 * remove rank set
	 * 
	 * @param RankSet
	 */
	void removeRankSet(String sId);

	/**
	 * update rank set
	 * 
	 * @param rankSet
	 */
	void updateRankSet(RankSet rankSet);

	/**
	 * insert rank set
	 * 
	 * @param rankSet
	 */
	void addRankSet(RankSet rankSet);

}
