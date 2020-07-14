package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService.Require;

/**
 * 
 * @author HieuLt
 *
 */
public class GetEmRankInforServiceTest {

	@Injectable
	private Require require;

	@Test
	public void insert(){
		List<String> lstSID = new ArrayList<String>();
		lstSID.add("0001");
		lstSID.add("0002");
		lstSID.add("0003");
		new Expectations() {
			{
				require.getAll(lstSID);
				result = true;
			}
			
		};

		
	}
}
