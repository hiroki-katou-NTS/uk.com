package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

public class EmploymentAppHistoryItemTest {
	
	/**
	 * Test [C-1] 期間で作成する
	 */
	@Test
	public void testConstruct1() {
		DatePeriod expDatePeriod = DatePeriod.daysFirstToLastIn(YearMonth.of(2022, 2));
		
		EmploymentAppHistoryItem domain = new EmploymentAppHistoryItem(expDatePeriod);
		
		assertThat(domain.getDatePeriod()).isEqualToComparingFieldByField(expDatePeriod);
	}
}
