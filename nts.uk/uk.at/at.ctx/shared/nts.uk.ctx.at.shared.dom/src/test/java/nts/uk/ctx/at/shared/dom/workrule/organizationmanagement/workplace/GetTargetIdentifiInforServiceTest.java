package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService.Require;

@RunWith(JMockit.class)
public class GetTargetIdentifiInforServiceTest {
	
	@Injectable
	private Require require;

	/**
	 * require.社員の所属組織を取得する( 基準日, list: 社員ID ) is empty
	 */
	@Test
	public void testGet() {
		GeneralDate referenceDate = GeneralDate.today();
		String empId ="employeeId";
		new Expectations() {
			{
				require.get(referenceDate, Arrays.asList(empId));
				
			}
		};
		
		
	}

}
