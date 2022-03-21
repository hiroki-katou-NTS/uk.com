package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;

public class SpecialLeaveAggregatePeriodWorkListTest {

	SpecialLeaveAggregatePeriodWorkList case1WorkList = new SpecialLeaveAggregatePeriodWorkList(
			Arrays.asList(
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 14)),GrantBeforeAfterAtr.BEFORE_GRANT,false, Optional.of(TypeTime.GRANT_SPECIFY_DATE)), 
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 15), GeneralDate.ymd(2022, 4, 30)),GrantBeforeAfterAtr.BEFORE_GRANT,false, Optional.of(TypeTime.GRANT_SPECIFY_DATE)), 
					work(new DatePeriod(GeneralDate.ymd(2022,5, 1), GeneralDate.ymd(2022, 5, 1)),GrantBeforeAfterAtr.AFTER_GRANT,true, Optional.of(TypeTime.GRANT_SPECIFY_DATE))));
	
	@Test
	public void case1() {
		
		GeneralDate entryDate = GeneralDate.ymd(2020,4, 1);
		
		SpecialLeaveAggregatePeriodWork no1 = case1WorkList.getPeriodWorkList().get(0);
		SpecialLeaveAggregatePeriodWork no2 = case1WorkList.getPeriodWorkList().get(1);
		SpecialLeaveAggregatePeriodWork no3 = case1WorkList.getPeriodWorkList().get(2);
		
		GrantBeforeAfterAtr no1a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case1WorkList, "isNextGrantPeriodAtr", no1,entryDate);
		assertThat(no1a).isEqualTo(GrantBeforeAfterAtr.BEFORE_GRANT);
		
		GrantBeforeAfterAtr no2a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case1WorkList, "isNextGrantPeriodAtr", no2,entryDate);
		assertThat(no2a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
		GrantBeforeAfterAtr no3a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case1WorkList, "isNextGrantPeriodAtr", no3,entryDate);
		assertThat(no3a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
	}

	
	
	SpecialLeaveAggregatePeriodWorkList case2WorkList = new SpecialLeaveAggregatePeriodWorkList(
			Arrays.asList(
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 14)),GrantBeforeAfterAtr.AFTER_GRANT,true, Optional.of(TypeTime.GRANT_PERIOD)), 
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 15), GeneralDate.ymd(2022, 4, 30)),GrantBeforeAfterAtr.AFTER_GRANT,true, Optional.of(TypeTime.GRANT_PERIOD)), 
					work(new DatePeriod(GeneralDate.ymd(2022,5, 1), GeneralDate.ymd(2022, 5, 1)),GrantBeforeAfterAtr.AFTER_GRANT,true, Optional.of(TypeTime.GRANT_PERIOD))));

	@Test
	public void case2() {
		
		GeneralDate entryDate = GeneralDate.ymd(2022,4, 1);
		
		SpecialLeaveAggregatePeriodWork no1 = case2WorkList.getPeriodWorkList().get(0);
		SpecialLeaveAggregatePeriodWork no2 = case2WorkList.getPeriodWorkList().get(1);
		SpecialLeaveAggregatePeriodWork no3 = case2WorkList.getPeriodWorkList().get(2);
		
		GrantBeforeAfterAtr no1a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case2WorkList, "isNextGrantPeriodAtr", no1,entryDate);
		assertThat(no1a).isEqualTo(GrantBeforeAfterAtr.BEFORE_GRANT);
		
		GrantBeforeAfterAtr no2a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case2WorkList, "isNextGrantPeriodAtr", no2,entryDate);
		assertThat(no2a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
		GrantBeforeAfterAtr no3a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case2WorkList, "isNextGrantPeriodAtr", no3,entryDate);
		assertThat(no3a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
	}
	
	
	
	SpecialLeaveAggregatePeriodWorkList case3WorkList = new SpecialLeaveAggregatePeriodWorkList(
			Arrays.asList(
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 14)),GrantBeforeAfterAtr.BEFORE_GRANT,false, Optional.empty()), 
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 15), GeneralDate.ymd(2022, 4, 30)),GrantBeforeAfterAtr.BEFORE_GRANT,false, Optional.empty()), 
					work(new DatePeriod(GeneralDate.ymd(2022,5, 1), GeneralDate.ymd(2022, 5, 1)),GrantBeforeAfterAtr.AFTER_GRANT,true, Optional.empty())));
	
	@Test
	public void case3() {
		
		GeneralDate entryDate = GeneralDate.ymd(2022,4, 1);
		
		SpecialLeaveAggregatePeriodWork no1 = case3WorkList.getPeriodWorkList().get(0);
		SpecialLeaveAggregatePeriodWork no2 = case3WorkList.getPeriodWorkList().get(1);
		SpecialLeaveAggregatePeriodWork no3 = case3WorkList.getPeriodWorkList().get(2);
		
		GrantBeforeAfterAtr no1a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case3WorkList, "isNextGrantPeriodAtr", no1,entryDate);
		assertThat(no1a).isEqualTo(GrantBeforeAfterAtr.BEFORE_GRANT);
		
		GrantBeforeAfterAtr no2a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case3WorkList, "isNextGrantPeriodAtr", no2,entryDate);
		assertThat(no2a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
		GrantBeforeAfterAtr no3a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(case3WorkList, "isNextGrantPeriodAtr", no3,entryDate);
		assertThat(no3a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
	}
	
	
	
	private SpecialLeaveAggregatePeriodWork work(DatePeriod period, GrantBeforeAfterAtr atr, boolean flg, Optional<TypeTime> type){
		return SpecialLeaveAggregatePeriodWork.of(period, 
				NextDayAfterPeriodEndWork.of(false, flg),
				new SpecialLeaveLapsedWork(),
				new SpecialLeaveGrantWork(false, type, Optional.empty()),
				atr);
	}
}
