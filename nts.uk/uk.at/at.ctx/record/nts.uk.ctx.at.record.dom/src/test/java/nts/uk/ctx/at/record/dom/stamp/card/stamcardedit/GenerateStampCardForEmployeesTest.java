package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.GenerateStampCardForEmployees.Require;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GenerateStampCardForEmployeesTest {
	
	@Injectable
	private Require require;
	
	private String companyId = "000001";
	private String contractCd = "000000";
	private String companyCd = "0001";
	private String sid = "123456";
	
	@Test
	public void testMsg_1756() {
		List<TargetPerson> persons = new ArrayList<>();
		
		NtsAssert.businessException("Msg_1756", () -> 
					GenerateStampCardForEmployees.generate(require, contractCd, companyCd, 
							EnumAdaptor.valueOf(1, MakeEmbossedCard.class), persons, companyId,sid));
	}
	
	@Test
	public void testMsg_1756_2() {
		List<TargetPerson> persons = new ArrayList<>();
		NtsAssert.businessException("Msg_1756", () -> 
					GenerateStampCardForEmployees.generate(require, contractCd, companyCd, 
							EnumAdaptor.valueOf(1, MakeEmbossedCard.class), persons, companyId,sid));
	}
	
	
	/**
	 * return Optional.of(stampCardEditing.editCardNumber(employeeCd));
	 */
	@Test
	public void test1() {
		List<TargetPerson> persons = new ArrayList<>();
		persons.add(new TargetPerson("000002", "8f9edce4-e135-4a1e-8dca-ad96abe405d6"));
		
		new Expectations() {
			{
				require.get(companyId);
				result = new StampCardEditing(companyId, new StampCardDigitNumber(20), StampCardEditMethod.AfterSpace);
				
				require.getByCardNoAndContractCode(anyString, contractCd);
				result = Optional.of(new StampCard(contractCd, "0123456", sid));
				
			}
		};
		

		List<ImprintedCardGenerationResult> cardGenerationResults = GenerateStampCardForEmployees.generate(require,
				contractCd, companyCd, EnumAdaptor.valueOf(1, MakeEmbossedCard.class), persons, companyId,sid);
		
		System.out.println(cardGenerationResults);
		assertThat(cardGenerationResults.get(0).getCardNumber().getStampNumber().v()).isEqualTo("0001000002          ");
	}
	
	@Test
	public void test2() {
		List<TargetPerson> persons = new ArrayList<>();
		persons.add(new TargetPerson("000002", "8f9edce4-e135-4a1e-8dca-ad96abe405d6"));
		
		new Expectations() {
			{
				require.get(companyId);
				result = new StampCardEditing(companyId, new StampCardDigitNumber(20), StampCardEditMethod.AfterSpace);
				
				require.getByCardNoAndContractCode(anyString, contractCd);
				result = Optional.of(new StampCard(contractCd, "0123456", sid));
				
			}
		};
		

		List<ImprintedCardGenerationResult> cardGenerationResults = GenerateStampCardForEmployees.generate(require,
				contractCd, companyCd, EnumAdaptor.valueOf(0, MakeEmbossedCard.class), persons, companyId,sid);
		
		assertThat(cardGenerationResults.get(0).getCardNumber().getStampNumber().v()).isEqualTo("000002              ");
	}
}
