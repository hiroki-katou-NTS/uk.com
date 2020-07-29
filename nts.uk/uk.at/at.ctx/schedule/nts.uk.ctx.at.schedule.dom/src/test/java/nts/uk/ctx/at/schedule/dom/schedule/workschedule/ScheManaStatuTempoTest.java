package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo.Require;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmploymentPeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.TempAbsenceFrameNo;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

@RunWith(JMockit.class)
public class ScheManaStatuTempoTest {

	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		ScheManaStatuTempo data = new ScheManaStatuTempo("employeeID", GeneralDate.today(), ScheManaStatus.CLOSED,
				Optional.empty(), Optional.empty());
		NtsAssert.invokeGetters(data);
	}

	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is empty
	 */
	@Test
	public void testCreate() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.SCHEDULE_MANAGEMENT);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is empty
	 */
	@Test
	public void testCreate_1() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.INVALID_DATA);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is not empty
	 * require.労働条件履歴を取得する( 社員ID, 年月日 ) is empty
	 */
	@Test
	public void testCreate_2() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		List<EmploymentPeriod> listEmploymentPeriod = Arrays.asList(new EmploymentPeriod(employeeID, "employmentCd", new DatePeriod(date, date), Optional.empty()));
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmploymentPeriod;
				
				require.getBySidAndStandardDate(employeeID, date);
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.INVALID_DATA);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is not empty
	 * require.労働条件履歴を取得する( 社員ID, 年月日 ) is not empty and ManageAtr == ManageAtr.NOTUSE
	 * 
	 */
	@Test
	public void testCreate_3() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		List<EmploymentPeriod> listEmploymentPeriod = Arrays.asList(new EmploymentPeriod(employeeID, "employmentCd", new DatePeriod(date, date), Optional.empty()));
		WorkingConditionItem workingConditionItem = new WorkingConditionItem(null, ManageAtr.NOTUSE, null, null, null, null, employeeID, null, null, null, null, null, null, null, null);
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmploymentPeriod;
				
				require.getBySidAndStandardDate(employeeID, date);
				result = Optional.of(workingConditionItem);
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.DO_NOT_MANAGE_SCHEDULE);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is not empty
	 * require.労働条件履歴を取得する( 社員ID, 年月日 ) is not empty and ManageAtr == ManageAtr.USE
	 * require.休職期間を取得する( 社員ID, 年月日 ) is not empty
	 */
	@Test
	public void testCreate_4() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		List<EmploymentPeriod> listEmploymentPeriod = Arrays.asList(new EmploymentPeriod(employeeID, "employmentCd", new DatePeriod(date, date), Optional.empty()));
		WorkingConditionItem workingConditionItem = new WorkingConditionItem(null, ManageAtr.USE, null, null, null, null, employeeID, null, null, null, null, null, null, null, null);
		List<EmployeeLeaveJobPeriodImport> listEmployeeLeaveJobPeriodImport = Arrays.asList(new EmployeeLeaveJobPeriodImport(employeeID, new DatePeriod(date, date)));
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmploymentPeriod;
				
				require.getBySidAndStandardDate(employeeID, date);
				result = Optional.of(workingConditionItem);
				
				require.getByDatePeriod(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmployeeLeaveJobPeriodImport;
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.ON_LEAVE);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is not empty
	 * require.労働条件履歴を取得する( 社員ID, 年月日 ) is not empty and ManageAtr == ManageAtr.USE
	 * require.休職期間を取得する( 社員ID, 年月日 ) is empty
	 * require.休業期間を取得する( 社員ID, 年月日 ) is not empty
	 */
	@Test
	public void testCreate_5() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		List<EmploymentPeriod> listEmploymentPeriod = Arrays.asList(new EmploymentPeriod(employeeID, "employmentCd", new DatePeriod(date, date), Optional.empty()));
		WorkingConditionItem workingConditionItem = new WorkingConditionItem(null, ManageAtr.USE, null, null, null, null, employeeID, null, null, null, null, null, null, null, null);
		TempAbsenceFrameNo tempAbsenceFrNo = new TempAbsenceFrameNo(BigDecimal.valueOf(1));
		List<EmpLeaveWorkPeriodImport> listEmpLeaveWorkPeriodImport = Arrays.asList(new EmpLeaveWorkPeriodImport(employeeID, tempAbsenceFrNo, new DatePeriod(date, date)));
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmploymentPeriod;
				
				require.getBySidAndStandardDate(employeeID, date);
				result = Optional.of(workingConditionItem);
				
				require.getByDatePeriod(Arrays.asList(employeeID), (DatePeriod) any);
				
				require.specAndGetHolidayPeriod(Arrays.asList(employeeID), (DatePeriod) any);
				result =listEmpLeaveWorkPeriodImport;
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.CLOSED);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}
	
	/**
	 * require.在籍期間を取得する( 社員ID, 年月日 ) is not empty
	 * require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ) is not empty
	 * require.労働条件履歴を取得する( 社員ID, 年月日 ) is not empty and ManageAtr == ManageAtr.USE
	 * require.休職期間を取得する( 社員ID, 年月日 ) is empty
	 * require.休業期間を取得する( 社員ID, 年月日 ) is empty
	 */
	@Test
	public void testCreate_6() {
		String employeeID = "employeeID";
		GeneralDate date = GeneralDate.today();
		List<AffCompanyHistSharedImport> listAffCompanyHistSharedImport = Arrays.asList(new AffCompanyHistSharedImport(employeeID, new ArrayList<>()));
		List<EmploymentPeriod> listEmploymentPeriod = Arrays.asList(new EmploymentPeriod(employeeID, "employmentCd", new DatePeriod(date, date), Optional.empty()));
		WorkingConditionItem workingConditionItem = new WorkingConditionItem(null, ManageAtr.USE, null, null, null, null, employeeID, null, null, null, null, null, null, null, null);
		new Expectations() {
			{
				require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), (DatePeriod) any);
				result = listAffCompanyHistSharedImport;
				
				require.getEmploymentHistory(Arrays.asList(employeeID), (DatePeriod) any);
				result = listEmploymentPeriod;
				
				require.getBySidAndStandardDate(employeeID, date);
				result = Optional.of(workingConditionItem);
				
				require.getByDatePeriod(Arrays.asList(employeeID), (DatePeriod) any);
				
				require.specAndGetHolidayPeriod(Arrays.asList(employeeID), (DatePeriod) any);
			}
		};
		ScheManaStatuTempo scheManaStatuTempo = ScheManaStatuTempo.create(require, employeeID, date);
		assertThat(scheManaStatuTempo.getEmployeeID()).isEqualTo(employeeID);
		assertThat(scheManaStatuTempo.getDate()).isEqualTo(date);
		assertThat(scheManaStatuTempo.getScheManaStatus()).isEqualTo(ScheManaStatus.CLOSED);
		assertThat(scheManaStatuTempo.getOptTempAbsenceFrameNo().isPresent()).isFalse();
		assertThat(scheManaStatuTempo.getOptEmploymentCd().isPresent()).isFalse();
	}

}
