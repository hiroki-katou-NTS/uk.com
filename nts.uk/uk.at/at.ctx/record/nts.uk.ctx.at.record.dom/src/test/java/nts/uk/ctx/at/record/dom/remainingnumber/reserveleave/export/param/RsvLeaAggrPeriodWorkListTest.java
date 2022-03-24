package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;

public class RsvLeaAggrPeriodWorkListTest {

	RsvLeaAggrPeriodWorkList periodWorkList = new RsvLeaAggrPeriodWorkList(
			Arrays.asList(work(new DatePeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 14)),GrantBeforeAfterAtr.BEFORE_GRANT,0,false), 
					work(new DatePeriod(GeneralDate.ymd(2022, 4, 15), GeneralDate.ymd(2022, 4, 30)),GrantBeforeAfterAtr.BEFORE_GRANT,0,false), 
					work(new DatePeriod(GeneralDate.ymd(2022,5, 1), GeneralDate.ymd(2022, 5, 1)),GrantBeforeAfterAtr.AFTER_GRANT,1,true)));
	
	@Test
	public void case1() {
		
		RsvLeaAggrPeriodWork no1 = periodWorkList.getPeriodWorkList().get(0);
		RsvLeaAggrPeriodWork no2 = periodWorkList.getPeriodWorkList().get(1);
		
		GrantBeforeAfterAtr no1a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no1);
		assertThat(no1a).isEqualTo(GrantBeforeAfterAtr.BEFORE_GRANT);
		
		GrantBeforeAfterAtr no2a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no2);
		assertThat(no2a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
		
	}
	
	@Test
	public void case2() {
		
		RsvLeaAggrPeriodWork no3 = periodWorkList.getPeriodWorkList().get(2);
		GrantBeforeAfterAtr no3a = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(periodWorkList, "isNextGrantPeriodAtr", no3);
		assertThat(no3a).isEqualTo(GrantBeforeAfterAtr.AFTER_GRANT);
	}

	
	
	
	private RsvLeaAggrPeriodWork work(DatePeriod period, GrantBeforeAfterAtr atr, int grantNumber, boolean flg){
		return new RsvLeaAggrPeriodWork(period,
				ReserveLeaveGrantWork.of(false, grantNumber, new MaxDaysRetention(0), atr,Optional.empty()),
				new ReserveLeaveLapsedWork(),
				RsvLeaNextDayAfterPeriodEndWork.of(false, flg));
				
				
	}
}
