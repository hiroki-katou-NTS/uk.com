package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.Arrays;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * ランクを新規登録する
 * 
 * @author sonnh1
 *
 */
public class InsertRankService {

	public static AtomTask insert(Require require, String companyId, RankCode rankCd, RankSymbol rankSymbol) {
		
		if (require.checkRankExist(companyId, rankCd)) {
			throw new BusinessException("Msg_3");
		}
		
		Rank newRank = new Rank(companyId, rankCd, rankSymbol);
		RankPriority rankPriority = require.getRankPriority(companyId);
		
		if (rankPriority == null) {
			RankPriority newRankPriority = new RankPriority(companyId, Arrays.asList(rankCd));
			
			return AtomTask.of(() -> {
				require.insertRank(newRank);
				require.insertRankPriority(newRankPriority);
			});
		}
		
		rankPriority.insert(rankCd);
		
		return AtomTask.of(() -> {
			require.insertRank(newRank);
			require.updateRankPriority(rankPriority);
		});
	}

	public static interface Require {
		// [R1] ランクがすでに登録されているか
		boolean checkRankExist(String companyId, RankCode rankCd);

		// [R2] ランクの優先順を取得する
		RankPriority getRankPriority(String companyId);

		// [R3] ランクを新規登録する
		void insertRank(Rank rank);

		// [R4] ランクの優先順を新規登録する
		void insertRankPriority(RankPriority rankPriority);

		// [R5] ランクの優先順を変更する
		void updateRankPriority(RankPriority rankPriority);
	}

}
