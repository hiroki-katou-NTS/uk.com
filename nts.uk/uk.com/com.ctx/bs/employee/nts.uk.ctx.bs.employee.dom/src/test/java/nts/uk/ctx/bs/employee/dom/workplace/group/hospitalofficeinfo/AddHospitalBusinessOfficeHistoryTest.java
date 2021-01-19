package nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.AddHospitalBusinessOfficeHistory.Require;
import nts.uk.shr.com.history.DateHistoryItem;;

@RunWith(JMockit.class)
public class AddHospitalBusinessOfficeHistoryTest {
	
	/**
	 * 職場グループの履歴がない場合
	 * @throws Exception 
	 */
	@Test
	public void add_no_history_Success() throws Exception{
		val workplaceGroupId = "wkpGroupId";
		val datePeriod = new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31));
		val nightShiftRule = NightShiftOperationRule.createByNightShiftNotUse();
		val require = this.expectationRequire(Optional.empty());
		NtsAssert.atomTask(
				() -> AddHospitalBusinessOfficeHistory.addHospitalBusinessOfficeHistory(
						require
						, workplaceGroupId
						, datePeriod
						, nightShiftRule
						, Optional.empty()));
	}


	/**
	 * 職場グループの履歴がある場合
	 * @throws Exception 
	 */
	@Test
	public void add_have_history_Success() throws Exception {
		val workplaceGroupId = "wkpGroupId";
		val oldDate = new DatePeriod(GeneralDate.ymd(1990, 1, 1), GeneralDate.ymd(9999, 12, 31));
		val newdatePeriod = new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31));
		val nightShiftRule = NightShiftOperationRule.createByNightShiftNotUse();
		val oldDateHistItem = DateHistoryItem.createNewHistory(oldDate);
		val hospitalHist = new HospitalBusinessOfficeInfoHistory(workplaceGroupId
				, new ArrayList<> (Arrays.asList(oldDateHistItem)) );
		val require = this.expectationRequire(Optional.of(hospitalHist));
		
		NtsAssert.atomTask(
				() -> AddHospitalBusinessOfficeHistory.addHospitalBusinessOfficeHistory(
						require
						, workplaceGroupId
						, newdatePeriod
						, nightShiftRule
						, Optional.empty())
				);
	}
	   
	private Require expectationRequire(Optional<HospitalBusinessOfficeInfoHistory> hospitalHist) throws Exception {
		Require mock = new MockUp<Require>() {
			@Mock
			void insertHospitalBusinessOfficeHistory(HospitalBusinessOfficeInfo hospitalInfo, HospitalBusinessOfficeInfoHistory hospitalHist) {
				
			}
			
			@Mock
			Optional<HospitalBusinessOfficeInfoHistory> getHospitalBusinessOfficeInfoHistory(String workplaceGroupId) {
				return hospitalHist;
			}
			
		}.getMockInstance();

		return mock;
	}

		
	
}
