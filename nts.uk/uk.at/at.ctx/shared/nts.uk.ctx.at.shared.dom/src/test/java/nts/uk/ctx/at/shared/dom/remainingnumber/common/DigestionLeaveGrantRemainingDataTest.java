package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingDataHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.AnnualPaidLeaveSettingHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

public class DigestionLeaveGrantRemainingDataTest {

	@Injectable
	private LeaveRemainingNumber.RequireM3 require;

	
	@Test
	public void getRemainingNumberCarriedForwardTest1() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0, 0, 0.0, null, null);
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,10.0,0));
		
		
		LeaveRemainingNumber totalRemainingNumber = nts.arc.testing.assertion.NtsAssert.Invoke.staticMethod(
				DigestionLeaveGrantRemainingData.class, "getRemainingNumberCarriedForward", 
				companyId, remainingDatas, usedNumber, baseDate, require);
		
		
		LeaveRemainingNumber expected =  LeaveRemainingNumber.of(new LeaveRemainingDayNumber(0.0),Optional.empty());
		
		assertThat(totalRemainingNumber.getDays()).isEqualTo(expected.getDays());
		assertThat(totalRemainingNumber.getMinutes()).isEqualTo(expected.getMinutes());
		
	}
	
	@Test
	public void getRemainingNumberCarriedForwardTest2() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0, 420, 0.0, null, null);
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2021, 4, 1), 10.0, 0, 0.0,0,0.0,300));
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,10.0,0));
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber totalRemainingNumber = nts.arc.testing.assertion.NtsAssert.Invoke.staticMethod(
				DigestionLeaveGrantRemainingData.class, "getRemainingNumberCarriedForward", 
				companyId, remainingDatas, usedNumber, baseDate, require);
		
		
		LeaveRemainingNumber expected = LeaveRemainingNumber.of(new LeaveRemainingDayNumber(0.0),Optional.empty());
		
		assertThat(totalRemainingNumber.getDays()).isEqualTo(expected.getDays());
		assertThat(totalRemainingNumber.getMinutes()).isEqualTo(expected.getMinutes());
		
	}
	
	
	@Test
	public void getRemainingNumberCarriedForwardTest3() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0, 0, 0.0, null, null);
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2021, 4, 1), 10.0, 0, 0.0,0,0.0,300));
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,10.0,0));
		
		LeaveRemainingNumber totalRemainingNumber = nts.arc.testing.assertion.NtsAssert.Invoke.staticMethod(
				DigestionLeaveGrantRemainingData.class, "getRemainingNumberCarriedForward", 
				companyId, remainingDatas, usedNumber, baseDate, require);
		
		
		LeaveRemainingNumber expected = new LeaveRemainingNumber(0.0,300);
		
		assertThat(totalRemainingNumber.getDays()).isEqualTo(expected.getDays());
		assertThat(totalRemainingNumber.getMinutes()).isEqualTo(expected.getMinutes());
		
	}
	
	
	@Test
	public void getRemainingNumberCarriedForwardTest4() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0, 240, 0.0, null, null);
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2021, 4, 1), 10.0, 0, 0.0,0,0.5,0));
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,10.0,0));
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber totalRemainingNumber = nts.arc.testing.assertion.NtsAssert.Invoke.staticMethod(
				DigestionLeaveGrantRemainingData.class, "getRemainingNumberCarriedForward", 
				companyId, remainingDatas, usedNumber, baseDate, require);
		
		
		LeaveRemainingNumber expected = LeaveRemainingNumber.of(new LeaveRemainingDayNumber(0.5),Optional.empty());
		
		assertThat(totalRemainingNumber.getDays()).isEqualTo(expected.getDays());
		assertThat(totalRemainingNumber.getMinutes()).isEqualTo(expected.getMinutes());
		
	}

	private LeaveGrantRemainingData leaveGrantRemainingData(String employeeId, GeneralDate date,double days, Integer minutes,
			double usedays, Integer useminutes, double remaindays, Integer remainminutes) {
		return LeaveGrantRemainingDataHelper.leaveGrantRemainingData(employeeId, date, days, minutes, usedays, useminutes, remaindays, remainminutes);
	}
	
	private AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId){
		return AnnualPaidLeaveSettingHelper.annualPaidLeaveSetting(companyId);
	}
}
