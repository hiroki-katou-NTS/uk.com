package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateDailyResultsStamps.Require;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class CreateDailyResultsStampsTest {

	@Injectable
	private Require require;
	
	/**
	 * if (!date.isPresent()) {
			return;
		}
	 */
	@Test
	public void test_date_null() {
		CreateDailyResultsStamps createDailyResultsStamps = new CreateDailyResultsStamps();
		createDailyResultsStamps.create(require, "DUMMY", "DUMMY", Optional.empty());
	}
	
	/**
	 * lstEmpId = require.getListEmpID = empty
	 */
	@Test
	public void test_lstEmpId_empty() {
		CreateDailyResultsStamps createDailyResultsStamps = new CreateDailyResultsStamps();
		createDailyResultsStamps.create(require, "DUMMY", "DUMMY", Optional.of(GeneralDate.today()));
	}
	
	/**
	 * lstEmpId = require.getListEmpID 
	 */
	@Test
	public void test_lstEmpId_not_empty() {
		List<ErrorMessageInfo> lstError = new ArrayList<>();
		List<String> lstEmpId = new ArrayList<>();
		
		lstError.add(new ErrorMessageInfo("DUMMY", "DUMMY", GeneralDate.today(), ExecutionContent.DAILY_CALCULATION, new ErrMessageResource("DUMMY"), new ErrMessageContent("DUMMY")));
		lstEmpId.add("DUMMY");
		
		new Expectations() {
			{
				require.getListEmpID("DUMMY", GeneralDate.today());
				result = lstEmpId;
				
				require.getListError("DUMMY", "DUMMY", null, 0, 0, null, 0);
				result = lstError;
			}
		};
		
		CreateDailyResultsStamps createDailyResultsStamps = new CreateDailyResultsStamps();
		createDailyResultsStamps.create(require, "DUMMY", "DUMMY", Optional.of(GeneralDate.today()));
	}
}
