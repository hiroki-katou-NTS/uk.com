package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.valueobject.RankWithPriority;

/**
 * ランクRepository
 * 
 * @author sonnh1
 *
 */
public interface RankRepository {
	/**
	 * ランクを取得する
	 * 
	 * @param companyId
	 * @return
	 */
	Rank getRank(String companyId, RankCode rankCd);

	/**
	 * ランクリストを取得する
	 * 
	 * @param companyId
	 * @param listRankCd
	 * @return
	 */
	List<Rank> getListRank(String companyId, List<RankCode> listRankCd);

	/**
	 * [2] コード順でランクリストを取得する
	 * @param companyId
	 * @return
	 */
	List<Rank> getListRank(String companyId);

	/**
	 * 
	 * @param rank
	 * @param rankPriority
	 */
	void insert(Rank rank, RankPriority rankPriority);

	/**
	 * 
	 * @param rank
	 */
	void updateRank(Rank rank);
	
	/**
	 * 
	 * @param rank
	 */
	void updateRankPriority(RankPriority rankPriority);

	/**
	 * 
	 * @param companyId
	 * @param rankCd
	 */
	void delete(String companyId, RankCode rankCd);

	/**
	 * 
	 * @param companyId
	 * @param rankCd
	 * @return
	 */
	boolean exist(String companyId, RankCode rankCd);

	/**
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<RankPriority> getRankPriority(String companyId);
	
	List<RankWithPriority> getListRankOrderbyPriority(String companyId);
}
