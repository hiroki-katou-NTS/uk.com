package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import nts.arc.task.tran.AtomTask;

/**
 * ランクを削除する
 * 
 * @author sonnh1
 *
 */
public class DeleteRankService {

	public static AtomTask delete(Require require, String companyId, RankCode rankCd) {
		RankPriority rankPriority = require.getRankPriority(companyId);

		if (rankPriority.getListRankCd().size() == 1) {
			return AtomTask.of(() -> {
				require.deleteRank(companyId, rankCd);
				require.deleteRankPriority(companyId);
			});
		}

		rankPriority.delete(rankCd);

		return AtomTask.of(() -> {
			require.deleteRank(companyId, rankCd);
			require.updateRankPriority(rankPriority);
		});
	}

	public static interface Require {
		// ランクの優先順を取得する
		RankPriority getRankPriority(String companyId);

		// ランクを削除する
		void deleteRank(String companyId, RankCode rankCd);

		// ランクの優先順を削除する
		void deleteRankPriority(String companyId);

		// ランクの優先順を変更する
		void updateRankPriority(RankPriority rankPriority);
	}

}
