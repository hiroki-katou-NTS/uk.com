package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.GenerateStampCardForEmployees.Require;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GenerateStampCardForEmployeesTest {
	
	@Injectable
	private Require require;
	
	private String companyId = "000000000000-0001";
	private String contractCd = "000000000000";
	private String companyCd = "0001";

	@Test
	public void testreturnEmpty() {
		List<TargetPerson> persons = new ArrayList<>();
		List<ImprintedCardGenerationResult> list = GenerateStampCardForEmployees.generate(require, contractCd, companyCd, EnumAdaptor.valueOf(0, MakeEmbossedCard.class), persons);
		
		assertThat(list).isEmpty();
	}
	
	@Test
	public void testMsg_1756() {
		List<TargetPerson> persons = new ArrayList<>();
		persons.add(new TargetPerson("000002", "8f9edce4-e135-4a1e-8dca-ad96abe405d6"));
		
		new Expectations() {
			{
				require.get(companyId);
				// result = new StampCardEditing(companyCd, new StampCardDigitNumber(10), EnumAdaptor.valueOf(1, StampCardEditMethod.class));
				
			}
		};
		
		NtsAssert.businessException("Msg_1756", () -> GenerateStampCardForEmployees.generate(require, contractCd, companyCd, EnumAdaptor.valueOf(1, MakeEmbossedCard.class), persons));
	}
}
