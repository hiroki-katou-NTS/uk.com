package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.InsertRankService.Require;
/**
 * 
 * @author sonnh1
 *
 */
public class InsertRankServiceTest {
	
	@Injectable
	private Require require; 

	@Test
	public void testInsert_Msg_3() {
		String companyId = "0001";
		RankCode rankCd = new RankCode("001");
		RankSymbol rankSymbol = new RankSymbol("A");
		
		new Expectations() {{
			require.checkRankExist(companyId, rankCd);
			result = RankPriorityHelper.checkExist(true);
		}};
		
		BusinessExceptionAssert.id("Msg_3", () -> InsertRankService.insert(require, companyId, rankCd, rankSymbol).run());
	}
	
	@Test
	public void testInsert_InsertRankPriority() {
		String companyId = "0001";
		RankCode rankCd = new RankCode("001");
		RankSymbol rankSymbol = new RankSymbol("A");
		
		new Expectations() {{
			require.checkRankExist(companyId, rankCd);
			result = RankPriorityHelper.checkExist(false);
			
			require.getRankPriority(companyId);
			result = RankPriorityHelper.setRankPriorityNull();
		}};
		
		InsertRankService.insert(require, companyId, rankCd, rankSymbol).run();
		
		new Verifications() {{
			require.insertRank((Rank) any);
			times = 1;
			
			require.insertRankPriority((RankPriority) any);
			times = 1;
			
			require.updateRankPriority((RankPriority) any);
			times = 0;
		}};
	}
	
	@Test
	public void testInsert_UpdateRankPriority() {
		String companyId = "0001";
		RankCode rankCd = new RankCode("099");
		RankSymbol rankSymbol = new RankSymbol("A");
		
		new Expectations() {{
			require.checkRankExist(companyId, rankCd);
			result = RankPriorityHelper.checkExist(false);
			
			require.getRankPriority(companyId);
			result = RankPriorityHelper.getRankPriorityBasic();
		}};
		
		InsertRankService.insert(require, companyId, rankCd, rankSymbol).run();
		
		new Verifications() {{
			require.insertRank((Rank) any);
			times = 1;
			
			require.insertRankPriority((RankPriority) any);
			times = 0;
			
			require.updateRankPriority((RankPriority) any);
			times = 1;
		}};
	}

}
