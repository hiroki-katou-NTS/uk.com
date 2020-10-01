package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.worktogether;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;
/**
 * 同時出勤指定
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class WorkTogetherTest {
	
	@Test
	public void getters() {
		val workTogether = WorkTogether.create(
				"EMPLOYEE_0",
				Arrays.asList(
						"EMPLOYEE_1", 
						"EMPLOYEE_2",
						"EMPLOYEE_3", 
						"EMPLOYEE_4",
						"EMPLOYEE_5"));
		
		NtsAssert.invokeGetters(workTogether);
		
	}
	
	/**
	 * inv-2 not 同時に出勤すべき社員の候補.contains( 社員ID )																	
	 * ケース: 同時に出勤すべき社員の候補の中に社員IDがいる -> Msg_1882
	 */
	@Test
	public void check_inv2_checkEmpMustWorkTogetherLstContainsEmployeeId() {
		NtsAssert.businessException("Msg_1882", ()-> {
			WorkTogether.create(
					"EMPLOYEE_1",
					Arrays.asList(
							"EMPLOYEE_1", 
							"EMPLOYEE_2"
							));
		});
	}
	
	/**
	 * inv-1: 0 <@同時に出勤すべき社員の候補.size() <= 10		
	 * ケース: 同時に出勤すべき社員の候補.size() = 0 -> Msg_1881
	 */
	@Test
	public void check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1881", ()-> {
			WorkTogether.create(
					"EMPLOYEE_1", Collections.emptyList());
		});
	}
	
	
	/**
	 * inv-1: 0 <@同時に出勤すべき社員の候補.size() <= 10		
	 * ケース: 同時に出勤すべき社員の候補.size() == 11-> Msg_1881
	 */
	@Test
	public void check_inv1_sizeAfter10() {
		NtsAssert.businessException("Msg_1881", ()-> {
			WorkTogether.create(
					"EMPLOYEE_0",
					Arrays.asList(
							"EMPLOYEE_1", 
							"EMPLOYEE_2",
							"EMPLOYEE_3", 
							"EMPLOYEE_4",
							"EMPLOYEE_5",
							"EMPLOYEE_6", 
							"EMPLOYEE_7",
							"EMPLOYEE_8",
							"EMPLOYEE_9",
							"EMPLOYEE_10",
							"EMPLOYEE_11"
							));
		});
	}
	

	
	/**
	 * 同時出勤指定を作成する：成功(success)
	 * 同時に出勤すべき社員の候補.size() = 10
	 * 同時に出勤すべき社員の候補の中に社員IDがない
	 */
	@Test
	public void create_WorkTogether_success() {
		val employeeIds = Arrays.asList(
				"EMPLOYEE_1", 
				"EMPLOYEE_2",
				"EMPLOYEE_3", 
				"EMPLOYEE_4",
				"EMPLOYEE_5",
				"EMPLOYEE_6", 
				"EMPLOYEE_7",
				"EMPLOYEE_8",
				"EMPLOYEE_9",
				"EMPLOYEE_10"
				);
		val workTogether =  WorkTogether.create("EMPLOYEE_0", employeeIds);
		
		assertThat(workTogether.getSid()).isEqualTo("EMPLOYEE_0");
		assertThat(workTogether.getEmpMustWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(employeeIds);
		
	}
	
}
