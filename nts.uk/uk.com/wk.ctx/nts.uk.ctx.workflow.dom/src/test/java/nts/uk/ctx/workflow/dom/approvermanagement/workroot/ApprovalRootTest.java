package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

public class ApprovalRootTest {

	/**
	 * Test [C-1] 就業システムで 期間と種類により作成する
	 */
	@Test
	public void testConstruct1() {
		DatePeriod expDatePeriod = DatePeriod.daysFirstToLastIn(YearMonth.of(2022, 2));
		EmploymentRootAtr expEmploymentRootAtr = EmploymentRootAtr.COMMON;
		ApplicationType expApplicationType = ApplicationType.ABSENCE_APPLICATION;
		ConfirmationRootType expConfirmationRootType = ConfirmationRootType.DAILY_CONFIRMATION;
		
		ApprovalRoot domain = new ApprovalRoot(expDatePeriod, 
				expEmploymentRootAtr, 
				expApplicationType,
				expConfirmationRootType);
		
		assertThat(domain.getSysAtr()).isEqualTo(SystemAtr.WORK);
		assertThat(domain.getEmploymentRootAtr()).isEqualTo(expEmploymentRootAtr);
		assertThat(domain.getApplicationType()).isEqualTo(expApplicationType);
		assertThat(domain.getConfirmationRootType()).isEqualTo(expConfirmationRootType);
		assertThat(domain.getNoticeId()).isNull();
		assertThat(domain.getBusEventId()).isNull();
		assertThat(domain.getHistoryItems().size()).isEqualTo(1);
		assertThat(domain.getHistoryItems().get(0).getDatePeriod()).isEqualToComparingFieldByField(expDatePeriod);
	}
}
