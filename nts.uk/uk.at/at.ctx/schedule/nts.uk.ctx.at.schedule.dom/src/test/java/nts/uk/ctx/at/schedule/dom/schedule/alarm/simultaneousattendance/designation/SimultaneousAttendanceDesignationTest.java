package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class SimultaneousAttendanceDesignationTest {
	
	@Test
	public void getters() {
		val simultaneousAttendanceDesignation = SimultaneousAttendanceDesignation.create(
				"517ef7f8-77d0-4eb0-b539-05e03a23f9e0",
				Arrays.asList(
						"EMPLOYEE_1", 
						"EMPLOYEE_2",
						"EMPLOYEE_3", 
						"EMPLOYEE_4",
						"EMPLOYEE_5"));
		
		NtsAssert.invokeGetters(simultaneousAttendanceDesignation);
		
	}
	
	/**
	 * inv-1: 0 <@同時に出勤すべき社員の候補.size() <= 10		
	 * ケース: 同時に出勤すべき社員の候補がemptyです -> Msg_1881
	 */
	@Test
	public void check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1881", ()-> {
			SimultaneousAttendanceDesignation.create(
					"EMPLOYEE_1", Collections.emptyList());
		});
	}
	
	
	/**
	 * inv-1: 0 <@同時に出勤すべき社員の候補.size() <= 10		
	 * ケース: 同時に出勤すべき社員の候補のsize > 10 -> Msg_1881
	 */
	@Test
	public void check_inv1_sizeAfter10() {
		
		/** ケース: 同時に出勤すべき社員の候補のsize <= 10 -> success*/
		SimultaneousAttendanceDesignation.create(
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
						"EMPLOYEE_10"
						));
		
		/** ケース: 同時に出勤すべき社員の候補のsize > 10 -> Msg_1881*/
		NtsAssert.businessException("Msg_1881", ()-> {
			SimultaneousAttendanceDesignation.create(
					"517ef7f8-77d0-4eb0-b539-05e03a23f9e0",
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
	 * inv-2 not 同時に出勤すべき社員の候補.contains( 社員ID )																	
	 * ケース: 同時に出勤すべき社員の候補の中に社員IDがいる -> Msg_1882
	 */
	@Test
	public void check_inv2_checkEmpMustWorkTogetherLstContainsEmployeeId() {
		
		/** ケース: 同時に出勤すべき社員の候補のsize <= 10 -> success*/
		SimultaneousAttendanceDesignation.create(
				"EMPLOYEE_1",
				Arrays.asList(
						"EMPLOYEE_2", 
						"EMPLOYEE_3",
						"EMPLOYEE_4"
						));
		
		/** ケース: 同時に出勤すべき社員の候補.contains( 社員ID ) == true -> Msg_1882*/
		NtsAssert.businessException("Msg_1881", ()-> {
			SimultaneousAttendanceDesignation.create(
					"EMPLOYEE_1",
					Arrays.asList(
							"EMPLOYEE_1", 
							"EMPLOYEE_2"
							));
		});
	}
	/**
	 * 同時出勤指定を作成する：成功(success)
	 * 
	 */
	@Test
	public void create_SimultaneousAttendanceDesignation_success() {
		val employeeIds = Arrays.asList(
				"EMPLOYEE_1", 
				"EMPLOYEE_2",
				"EMPLOYEE_3", 
				"EMPLOYEE_4",
				"EMPLOYEE_5",
				"EMPLOYEE_6", 
				"EMPLOYEE_7",
				"EMPLOYEE_8",
				"EMPLOYEE_9"
				);
		val simulAttDesign =  SimultaneousAttendanceDesignation.create("EMPLOYEE_0", employeeIds);
		
		assertThat(simulAttDesign.getSid()).isEqualTo("EMPLOYEE_0");
		assertThat(simulAttDesign.getEmpMustWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(employeeIds);
		
	}
	
}
