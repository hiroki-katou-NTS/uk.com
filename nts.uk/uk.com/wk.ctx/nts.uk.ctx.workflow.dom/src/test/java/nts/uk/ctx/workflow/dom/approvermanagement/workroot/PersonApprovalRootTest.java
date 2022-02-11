package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings.OperationMode;

public class PersonApprovalRootTest {

	/**
	 * Test [C-1] 就業システムで 期間と種類により作成する
	 */
	@Test
	public void testConstruct1() {
		String expCompanyId = "exp-companyId";
		String expEmployeeId = "exp-employeeId";
		DatePeriod expDatePeriod = DatePeriod.daysFirstToLastIn(YearMonth.of(2022, 2));
		EmploymentRootAtr expEmploymentRootAtr = EmploymentRootAtr.COMMON;
		ApplicationType expApplicationType = ApplicationType.ABSENCE_APPLICATION;
		ConfirmationRootType expConfirmationRootType = ConfirmationRootType.DAILY_CONFIRMATION;
		OperationMode expOperationMode = OperationMode.SUPERIORS_EMPLOYEE; 
		ApprovalRoot expApprovalRoot = new ApprovalRoot(expDatePeriod, expEmploymentRootAtr, expApplicationType, expConfirmationRootType);
		
		PersonApprovalRoot domain = new PersonApprovalRoot(expCompanyId,
				expEmployeeId,
				expDatePeriod,
				expEmploymentRootAtr,
				expApplicationType,
				expConfirmationRootType);
		
		assertThat(domain.getCompanyId()).isEqualTo(expCompanyId);
		assertThat(domain.getEmployeeId()).isEqualTo(expEmployeeId);
		assertThat(domain.getApprRoot()).isEqualToComparingOnlyGivenFields(expApprovalRoot,
				"sysAtr",
				"employmentRootAtr",
				"applicationType",
				"confirmationRootType",
				"noticeId",
				"busEventId");
		assertThat(domain.getOperationMode()).isEqualTo(expOperationMode);
		
	}
}
