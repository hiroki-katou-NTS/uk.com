package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.AutoCreateStampCardNumberService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class AutoCreateStampCardNumberServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * !stampMeans.checkAutoCreateStamp()
	 */
	@Test
	public void test_create_1() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.TEXT);
		
		assertThat(optional).isEmpty();
	}
	
	/**
	 * !stampCard.isPresent()
	 */
	@Test
	public void test_create_2() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional).isEmpty();
	}
	
	/**
	 * !companyInfoOpt.isPresent()
	 */
	@Test
	public void test_createStampCard_1() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
				result = AutoCreateStampCardNumberServiceHelper.getListEmployeeDataMngInfoImport();
				
				require.getCompanyNotAbolitionByCid("DUMMY");
			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional).isEmpty();
	}
	
	/**
	 * !stampCardEditingOpt.isPresent()
	 */
	@Test
	public void test_createStampCardNumber_1() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
				result = AutoCreateStampCardNumberServiceHelper.getListEmployeeDataMngInfoImport();
				
				require.getCompanyNotAbolitionByCid("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getCompanyImport622();
				
				require.get("DUMMY");

			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional).isEmpty();
	}

	/**
	 * !stampCardNumberOpt.isPresent()
	 */
	@Test
	public void test_createStampCardNumber_2() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
				result = AutoCreateStampCardNumberServiceHelper.getListEmployeeDataMngInfoImport();
				
				require.getCompanyNotAbolitionByCid("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getCompanyImport622();
				
				require.get("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getStampCardEditing_Null();

			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional).isEmpty();
	}
	
	/**
	 * stampOpt.isPresent()
	 */
	@Test
	public void test_createStampCardNumber_3() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
				result = AutoCreateStampCardNumberServiceHelper.getListEmployeeDataMngInfoImport();
				
				require.getCompanyNotAbolitionByCid("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getCompanyImport622();
				
				require.get("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getStampCardEditing();
				
				require.getByCardNoAndContractCode(anyString, anyString);
				result = AutoCreateStampCardNumberServiceHelper.getStamp();

			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional.isPresent()).isFalse();
	}
	

	@Test
	public void test() {
		AutoCreateStampCardNumberService service = new AutoCreateStampCardNumberService();
		
		new Expectations() {
			{
				require.findBySidNotDel(Arrays.asList("DUMMY"));
				result = AutoCreateStampCardNumberServiceHelper.getListEmployeeDataMngInfoImport();
				
				require.getCompanyNotAbolitionByCid("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getCompanyImport622();
				
				require.get("DUMMY");
				result = AutoCreateStampCardNumberServiceHelper.getStampCardEditing();
				
				//require.getByCardNoAndContractCode("DUMMY", "DUMMY");

			}
		};
		
		Optional<StampCardCreateResult> optional = service.create(require, "DUMMY", StampMeans.SMART_PHONE);
		
		assertThat(optional.isPresent()).isTrue();
		
		NtsAssert.atomTask(
				() -> optional.get().getAtomTask().get(),
				any -> require.add((StampCard) any.get())
		);
	}
}
