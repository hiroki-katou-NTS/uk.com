package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetMaximumNightShiftHoursOfEmployeeService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NightShiftUpperLimitTime;
/**
 * UTコード：社員の夜勤上限時間を取得する
 * @author lan_lt
 *
 */
public class GetMaximumNightShiftHoursOfEmployeeServiceTest {
	@Injectable
	private GetMaximumNightShiftHoursOfEmployeeService.Require require;
	
	private String sid;
	
	private GeneralDate baseDate;
	
	@Before
	public void initData() {
		this.sid = "sid";
		this.baseDate = GeneralDate.ymd(2021, 2, 24);
	}
	
	/**
	 * 社員の医療勤務形態履歴項目を取得する (社員ID, 基準日) = empty
	 * output: empty
	 */
	@Test
	public void getEmpMedicalWorkFormHisItem_empty() {
		new Expectations() {
			{
				require.getEmpMedicalWorkFormHisItem((String) any, (GeneralDate) any);
				
			}
		};
		
		Optional<AttendanceTimeMonth> result = GetMaximumNightShiftHoursOfEmployeeService.get(require, this.sid,  this.baseDate);
		
		assertThat(result).isEmpty();
		
	}
	
	/**
	 * 夜勤専従か = true
	 * output: 夜勤専従者
	 */
	@Test
	public void getNightShiftWorkerHours(@Mocked EmpMedicalWorkStyleHistoryItem empMedicalHisItem) {
		val maxNightShiftHours = new NightShiftUpperLimitTime("contractCode", new AttendanceTimeMonth(720), new AttendanceTimeMonth(360));
		
		new Expectations() {
			{
				require.getEmpMedicalWorkFormHisItem((String) any, (GeneralDate) any);
				result = Optional.of(empMedicalHisItem);
				
				require.getNightShiftUpperLimitTime();
				result = maxNightShiftHours;
				
				empMedicalHisItem.isOnlyNightShift();
				result = true;
			}
		};
		
		Optional<AttendanceTimeMonth> result = GetMaximumNightShiftHoursOfEmployeeService.get(require, this.sid,  this.baseDate);
		
		assertThat(result).isPresent();
		
		assertThat(result.get()).isEqualTo(maxNightShiftHours.getNightShiftWorker());
		
	}
	
	/**
	 * 夜勤専従か = false
	 * output: 夜勤専従者以外
	 */
	@Test
	public void getRegularWorkerHours(@Mocked EmpMedicalWorkStyleHistoryItem empMedicalHisItem) {
		val maxNightShiftHours = new NightShiftUpperLimitTime("contractCode", new AttendanceTimeMonth(720), new AttendanceTimeMonth(360));
		
		new Expectations() {
			{
				require.getEmpMedicalWorkFormHisItem((String) any, (GeneralDate) any);
				result = Optional.of(empMedicalHisItem);
				
				require.getNightShiftUpperLimitTime();
				result = maxNightShiftHours;
				
				empMedicalHisItem.isOnlyNightShift();
				result = false;
			}
		};
		
		Optional<AttendanceTimeMonth> result = GetMaximumNightShiftHoursOfEmployeeService.get(require, this.sid,  this.baseDate);
		
		assertThat(result).isPresent();
		
		assertThat(result.get()).isEqualTo(maxNightShiftHours.getRegularWorker());
		
	}
}
