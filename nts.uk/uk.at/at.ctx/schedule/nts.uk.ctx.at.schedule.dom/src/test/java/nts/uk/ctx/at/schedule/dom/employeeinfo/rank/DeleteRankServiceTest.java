package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.DeleteRankService.Require;

public class DeleteRankServiceTest {

	@Injectable
	private Require require;

	/**
	 * test delete function when size of list rank code = 1
	 */
	@Test
	public void testDelete_OneItem() {
		String companyId = "0001";
		RankCode rankCd = new RankCode("001");
		
		new Expectations() {{
				require.getRankPriority(companyId);
				result = RankPriorityHelper.getListRankCode1Item();
		}};
		
		DeleteRankService.delete(require, companyId, rankCd).run();
		
		new Verifications() {{
			require.deleteRank(companyId, rankCd);
			times = 1;
			
			require.deleteRankPriority(companyId);
			times = 1;
			
			require.updateRankPriority((RankPriority) any);
			times = 0;
		}};
	}
	
	/**
	 * test delete function when size of list rank code > 1
	 */
	@Test
	public void testDelete_MultiItems() {
		String companyId = "0001";
		RankCode rankCd = new RankCode("002");
		
		new Expectations() {{
				require.getRankPriority(companyId);
				result = RankPriorityHelper.getRankPriorityBasic();
		}};
		
		DeleteRankService.delete(require, companyId, rankCd).run();
		
		new Verifications() {{
			require.deleteRank(companyId, rankCd);
			times = 1;
			
			require.deleteRankPriority(companyId);
			times = 0;
			
			require.updateRankPriority((RankPriority) any);
			times = 1;
		}};
	}

}
