package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.GenerateStampCardForEmployees.Require;
<<<<<<< HEAD
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
=======
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GenerateStampCardForEmployeesTest {
	
	@Injectable
	private Require require;
	
<<<<<<< HEAD
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
		
		new Expectations() {
			{
				require.get(companyId);
				
				require.getByCardNoAndContractCode("DUMMY", contractCd);
				
			}
		};
		
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
				
				require.getByCardNoAndContractCode("DUMMY", contractCd);
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
=======
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
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest
		List<TargetPerson> persons = new ArrayList<>();
		persons.add(new TargetPerson("000002", "8f9edce4-e135-4a1e-8dca-ad96abe405d6"));
		
		new Expectations() {
			{
				require.get(companyId);
<<<<<<< HEAD
				result = new StampCardEditing(companyId, new StampCardDigitNumber(20), StampCardEditMethod.AfterSpace);
				
				require.getByCardNoAndContractCode("DUMMY", contractCd);
				result = Optional.of(new StampCard(contractCd, "0123456", sid));
=======
				// result = new StampCardEditing(companyCd, new StampCardDigitNumber(10), EnumAdaptor.valueOf(1, StampCardEditMethod.class));
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest
				
			}
		};
		
<<<<<<< HEAD

		List<ImprintedCardGenerationResult> cardGenerationResults = GenerateStampCardForEmployees.generate(require,
				contractCd, companyCd, EnumAdaptor.valueOf(0, MakeEmbossedCard.class), persons, companyId,sid);
		
		assertThat(cardGenerationResults.get(0).getCardNumber().getStampNumber().v()).isEqualTo("000002              ");
=======
		NtsAssert.businessException("Msg_1756", () -> GenerateStampCardForEmployees.generate(require, contractCd, companyCd, EnumAdaptor.valueOf(1, MakeEmbossedCard.class), persons));
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest
	}
}
