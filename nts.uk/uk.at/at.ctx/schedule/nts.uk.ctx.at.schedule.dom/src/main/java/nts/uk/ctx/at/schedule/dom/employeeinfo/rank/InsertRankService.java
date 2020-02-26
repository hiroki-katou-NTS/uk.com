package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.Arrays;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * ランクを新規登録する
 * 
 * @author sonnh1
 *
 */
public class InsertRankService {

	//[1] ランクを新規登録する
	public static AtomTask insert(Require require, String companyId, RankCode rankCd, RankSymbol rankSymbol) {

		if (require.checkRankExist(companyId, rankCd)) {
			throw new BusinessException("Msg_3");
		}

		Rank newRank = new Rank(companyId, rankCd, rankSymbol);
		Optional<RankPriority> optRankPriority = require.getRankPriority(companyId);
		RankPriority rankPriority = null;
		
		if (!optRankPriority.isPresent()) {
			rankPriority = new RankPriority(companyId, Arrays.asList(rankCd));
		} else {
			rankPriority = optRankPriority.get();
			rankPriority.insert(rankCd);
		}

		RankPriority newRankPriority = rankPriority;

		return AtomTask.of(() -> {
			require.insertRank(newRank, newRankPriority);
		});
	}

	public static interface Require {
		// [R1] ランクがすでに登録されているか
		boolean checkRankExist(String companyId, RankCode rankCd);

		// [R2] ランクの優先順を取得する
		Optional<RankPriority> getRankPriority(String companyId);

		// [R3] ランクを新規登録する
		void insertRank(Rank rank, RankPriority rankPriority);
	}

}
