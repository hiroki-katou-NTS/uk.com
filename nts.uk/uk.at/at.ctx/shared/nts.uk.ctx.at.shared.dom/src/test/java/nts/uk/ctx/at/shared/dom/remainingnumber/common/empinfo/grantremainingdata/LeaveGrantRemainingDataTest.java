package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.AnnualPaidLeaveSettingHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

public class LeaveGrantRemainingDataTest {

	@Injectable
	private LeaveRemainingNumber.RequireM3 require;
	
	/**
	 * 残数10日
	 * 1件目使用数1日　2件目4時間
	 * 消化できる
	 */
	@Test
	public void test1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		List<LeaveUsedNumber> usedNumberList = new ArrayList<>();
		usedNumberList.add(new LeaveUsedNumber(1.0, 0, 0.0, null, null));  // 1.0日
		usedNumberList.add(new LeaveUsedNumber(0.0, 240, 0.0, null, null));  // 4時間
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,10.0,0));
		List<LeaveGrantRemainingData> dummyList = new ArrayList<>();
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		for(LeaveUsedNumber usedNumber :usedNumberList){
			RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();
			
			Optional<LeaveGrantRemainingData> dummy = LeaveGrantRemainingData.digest(require, remainingDatas,
					remNumShiftListWork, usedNumber, companyId, employeeId, baseDate);
			
			if(dummy.isPresent()){
				dummyList.add(dummy.get());
			}
		}
		
		LeaveGrantRemainingData expected = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 10.0, 0, 1.0, 240, 8, 240);
		
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getDays()).isEqualTo(expected.getDetails().getGrantNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getMinutes()).isEqualTo(expected.getDetails().getGrantNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expected.getDetails().getUsedNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expected.getDetails().getUsedNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expected.getDetails().getRemainingNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expected.getDetails().getRemainingNumber().getMinutes());
		
	}
	
	/**
	 * 1件目残数0日5時間　2件目10日
	 * 1件目使用数1日　2件目4時間
	 * 1件目で消化できず、5時間が繰り越されて消化される
	 */
	@Test
	public void test2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		List<LeaveUsedNumber> usedNumberList = new ArrayList<>();
		usedNumberList.add(new LeaveUsedNumber(1.0, 0, 0.0, null, null));  // 1.0日
		usedNumberList.add(new LeaveUsedNumber(0.0, 240, 0.0, null, null));  // 4時間
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2021, 4, 1), 10.0, 0, 9.0,180, 0.0, 300));
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0, 10.0, 0));
		List<LeaveGrantRemainingData> dummyList = new ArrayList<>();
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		for(LeaveUsedNumber usedNumber :usedNumberList){
			RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();
			
			Optional<LeaveGrantRemainingData> dummy = LeaveGrantRemainingData.digest(require, remainingDatas,
					remNumShiftListWork, usedNumber, companyId, employeeId, baseDate);
			
			if(dummy.isPresent()){
				dummyList.add(dummy.get());
			}
		}
		
		LeaveGrantRemainingData expected1 = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 10.0, 0, 9.0,180, 0.0, 0);
		
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getDays()).isEqualTo(expected1.getDetails().getGrantNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getMinutes()).isEqualTo(expected1.getDetails().getGrantNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expected1.getDetails().getUsedNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expected1.getDetails().getUsedNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expected1.getDetails().getRemainingNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expected1.getDetails().getRemainingNumber().getMinutes());
		
		LeaveGrantRemainingData expected2 = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 10.0, 0, 1.0,240, 9.0, 60);
		
		assertThat(remainingDatas.get(1).getDetails().getGrantNumber().getDays()).isEqualTo(expected2.getDetails().getGrantNumber().getDays());
		assertThat(remainingDatas.get(1).getDetails().getGrantNumber().getMinutes()).isEqualTo(expected2.getDetails().getGrantNumber().getMinutes());
		assertThat(remainingDatas.get(1).getDetails().getUsedNumber().getDays()).isEqualTo(expected2.getDetails().getUsedNumber().getDays());
		assertThat(remainingDatas.get(1).getDetails().getUsedNumber().getMinutes()).isEqualTo(expected2.getDetails().getUsedNumber().getMinutes());
		assertThat(remainingDatas.get(1).getDetails().getRemainingNumber().getDays()).isEqualTo(expected2.getDetails().getRemainingNumber().getDays());
		assertThat(remainingDatas.get(1).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expected2.getDetails().getRemainingNumber().getMinutes());

	}

	/**
	 * 1件目残数0日5時間
	 * 1件目使用数1日　2件目4時間
	 * 2件とも消化できず、ダミーデータが2件作成される
	 */
	@Test
	public void test3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		List<LeaveUsedNumber> usedNumberList = new ArrayList<>();
		usedNumberList.add(new LeaveUsedNumber(1.0, 0, 0.0, null, null));  // 1.0日
		usedNumberList.add(new LeaveUsedNumber(0.0, 180, 0.0, null, null));  // 4時間
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,0.0, 300));
		List<LeaveGrantRemainingData> dummyList = new ArrayList<>();
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		for(LeaveUsedNumber usedNumber :usedNumberList){
			RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();
			
			Optional<LeaveGrantRemainingData> dummy = LeaveGrantRemainingData.digest(require, remainingDatas,
					remNumShiftListWork, usedNumber, companyId, employeeId, baseDate);
			
			if(dummy.isPresent()){
				dummyList.add(dummy.get());
			}
		}
		
		LeaveGrantRemainingData expected = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0, 0, 0.0, 0);
		
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getDays()).isEqualTo(expected.getDetails().getGrantNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getMinutes()).isEqualTo(expected.getDetails().getGrantNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expected.getDetails().getUsedNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expected.getDetails().getUsedNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expected.getDetails().getRemainingNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expected.getDetails().getRemainingNumber().getMinutes());
		
		LeaveGrantRemainingData expectedDummy1 = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 0.0, 0, 1.0, 0, -1.0, 0);
		
		assertThat(dummyList.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expectedDummy1.getDetails().getUsedNumber().getDays());
		assertThat(dummyList.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expectedDummy1.getDetails().getUsedNumber().getMinutes());
		assertThat(dummyList.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expectedDummy1.getDetails().getRemainingNumber().getDays());
		assertThat(dummyList.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expectedDummy1.getDetails().getRemainingNumber().getMinutes());

		LeaveGrantRemainingData expectedDummy2 = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 0.0, 0, 0.0, 180, 0.0, -180);
		
		assertThat(dummyList.get(1).getDetails().getUsedNumber().getDays()).isEqualTo(expectedDummy2.getDetails().getUsedNumber().getDays());
		assertThat(dummyList.get(1).getDetails().getUsedNumber().getMinutes()).isEqualTo(expectedDummy2.getDetails().getUsedNumber().getMinutes());
		assertThat(dummyList.get(1).getDetails().getRemainingNumber().getDays()).isEqualTo(expectedDummy2.getDetails().getRemainingNumber().getDays());
		assertThat(dummyList.get(1).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expectedDummy2.getDetails().getRemainingNumber().getMinutes());

	}
	/**
	 * 1件目残数0日5時間　2件目10日
	 * 1件目4時間　2件目使用数1日
	 * 2件目が消化できず、ダミーデータが1件目作成される
	 */
	@Test
	public void test4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		List<LeaveUsedNumber> usedNumberList = new ArrayList<>();
		usedNumberList.add(new LeaveUsedNumber(0.0, 180, 0.0, null, null));  // 4時間
		usedNumberList.add(new LeaveUsedNumber(1.0, 0, 0.0, null, null));  // 1.0日
		
		List<LeaveGrantRemainingData> remainingDatas= new ArrayList<>();
		remainingDatas.add(leaveGrantRemainingData(employeeId,GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0,0,0.0, 300));
		List<LeaveGrantRemainingData> dummyList = new ArrayList<>();
		
		
		for(LeaveUsedNumber usedNumber :usedNumberList){
			RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();
			
			Optional<LeaveGrantRemainingData> dummy = LeaveGrantRemainingData.digest(require, remainingDatas,
					remNumShiftListWork, usedNumber, companyId, employeeId, baseDate);
			
			if(dummy.isPresent()){
				dummyList.add(dummy.get());
			}
		}
		
		LeaveGrantRemainingData expected = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 10.0, 0, 0.0, 180, 0.0, 0);
		
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getDays()).isEqualTo(expected.getDetails().getGrantNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getGrantNumber().getMinutes()).isEqualTo(expected.getDetails().getGrantNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expected.getDetails().getUsedNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expected.getDetails().getUsedNumber().getMinutes());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expected.getDetails().getRemainingNumber().getDays());
		assertThat(remainingDatas.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expected.getDetails().getRemainingNumber().getMinutes());
		
		LeaveGrantRemainingData expectedDummy1 = leaveGrantRemainingData(employeeId, GeneralDate.ymd(2022, 4, 1), 0.0, 0, 1.0, 0, -1.0, 0);
		
		assertThat(dummyList.get(0).getDetails().getUsedNumber().getDays()).isEqualTo(expectedDummy1.getDetails().getUsedNumber().getDays());
		assertThat(dummyList.get(0).getDetails().getUsedNumber().getMinutes()).isEqualTo(expectedDummy1.getDetails().getUsedNumber().getMinutes());
		assertThat(dummyList.get(0).getDetails().getRemainingNumber().getDays()).isEqualTo(expectedDummy1.getDetails().getRemainingNumber().getDays());
		assertThat(dummyList.get(0).getDetails().getRemainingNumber().getMinutes()).isEqualTo(expectedDummy1.getDetails().getRemainingNumber().getMinutes());

	}
	
	private LeaveGrantRemainingData leaveGrantRemainingData(String employeeId, GeneralDate date,double days, Integer minutes,
			double usedays, Integer useminutes, double remaindays, Integer remainminutes) {
		return LeaveGrantRemainingDataHelper.leaveGrantRemainingData(employeeId, date, days, minutes, usedays, useminutes, remaindays, remainminutes);
	}
	
	private AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId){
		return AnnualPaidLeaveSettingHelper.annualPaidLeaveSetting(companyId);
	}
}
