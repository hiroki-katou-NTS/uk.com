package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import static org.assertj.core.api.Assertions.assertThat;

public class AggregatePeriodWorkListTest {

	AggregatePeriodWorkList periodWorkList = new AggregatePeriodWorkList(
			Arrays.asList(work(new DatePeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 14)),GrantBeforeAfterAtr.BEFORE_GRANT,false), 
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 15), GeneralDate.ymd(2022, 4, 30)),GrantBeforeAfterAtr.BEFORE_GRANT,false), 
					work(new DatePeriod(GeneralDate.ymd(2022,5, 1), GeneralDate.ymd(2022, 5, 1)),GrantBeforeAfterAtr.AFTER_GRANT,true)));
	
	@Test
	public void case1() {
		
		AggregatePeriodWork no1 = periodWorkList.getPeriodWorkList().get(0);
		AggregatePeriodWork no2 = periodWorkList.getPeriodWorkList().get(1);
		
		GrantBeforeAfterAtr no1a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no1);
		assertThat(no1a).isEqualTo(GrantBeforeAfterAtr.BEFORE_GRANT);
		
		GrantBeforeAfterAtr no2a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no2);
		assertThat(no2a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
	}
	
	@Test
	public void case2() {
		
		AggregatePeriodWork no3 = periodWorkList.getPeriodWorkList().get(2);
		GrantBeforeAfterAtr no3a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no3);
		assertThat(no3a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
	}
	
	private AggregatePeriodWork work(DatePeriod period, GrantBeforeAfterAtr atr, boolean flg) {
		return new AggregatePeriodWork(period, 
				new AnnualLeaveLapsedWork(),
				new AnnualLeaveGrantWork(),
				AnnualNextDayAfterPeriodEndWork.of(false, flg),
				atr);
		
	}
	
}
