package nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

@RunWith(JMockit.class)
public class AddHospitalBusinessOfficeHistoryTest {

	@Injectable
	private AddHospitalBusinessOfficeHistory.Require require;
	
	/**
	 * 職場グループの履歴がない場合
	 */
	@Test
	public void add_no_history_Success() {
		
		val workplaceGroupId = "wkpGroupId";
		
		val datePeriod = new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31));
		
		val nightShiftRule = NightShiftOperationRule.createByNightShiftNotUse();
		
		new Expectations() {
			{
				require.getHospitalBusinessOfficeInfoHistory(workplaceGroupId, datePeriod.end());
				
			}
		};
		
		NtsAssert.atomTask(
				() -> AddHospitalBusinessOfficeHistory.addHospitalBusinessOfficeHistory(
						require
						, workplaceGroupId
						, datePeriod
						, nightShiftRule
						, Optional.empty()), 
				any -> require.insertHospitalBusinessOfficeHistory(any.get(),  any.get()));
	}

	
	
	/**
	 * 職場グループの履歴がある場合
	 */
	@Test
	public void add_have_history_Success() {
		
		val workplaceGroupId = "wkpGroupId";
		
		val oldDate = new DatePeriod(GeneralDate.ymd(1990, 1, 1), GeneralDate.ymd(9999, 12, 31));
		
		val newdatePeriod = new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31));
		
		val nightShiftRule = NightShiftOperationRule.createByNightShiftNotUse();
		
		val oldDateHistItem = DateHistoryItem.createNewHistory(oldDate);
		
		val hospitalHist = new HospitalBusinessOfficeInfoHistory(workplaceGroupId
				, new ArrayList<> (Arrays.asList(oldDateHistItem)) );
		
		new Expectations() {
			{
				require.getHospitalBusinessOfficeInfoHistory(workplaceGroupId, newdatePeriod.end());
				result = Optional.of(hospitalHist);
				
			}
		};
		
		NtsAssert.atomTask(
				() -> AddHospitalBusinessOfficeHistory.addHospitalBusinessOfficeHistory(
						require
						, workplaceGroupId
						, newdatePeriod
						, nightShiftRule
						, Optional.empty()), 
				any -> require.insertHospitalBusinessOfficeHistory(any.get(),  any.get())
				
				);
	}
	
}
