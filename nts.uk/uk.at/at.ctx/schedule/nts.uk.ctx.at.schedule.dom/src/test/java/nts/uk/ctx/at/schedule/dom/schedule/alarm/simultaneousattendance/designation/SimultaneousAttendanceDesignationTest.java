package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation;

import java.util.ArrayList;
import java.util.Arrays;

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
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e1", 
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e3", 
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e5"));
		
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
					"517ef7f8-77d0-4eb0-b539-05e03a23f9e0", new ArrayList<>());
		});
	}
	
	
	/**
	 * inv-1: 0 <@同時に出勤すべき社員の候補.size() <= 10		
	 * ケース: 同時に出勤すべき社員の候補のsize > 10 -> Msg_1881
	 */
	@Test
	public void check_inv1_sizeAfter10() {
		NtsAssert.businessException("Msg_1881", ()-> {
			SimultaneousAttendanceDesignation.create(
					"517ef7f8-77d0-4eb0-b539-05e03a23f9e0",
					Arrays.asList(
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e1", 
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e3", 
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e5",
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e6", 
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e7",
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e8",
							"517ef7f8-77d0-4eb0-b539-05e03a23f9e9",
							"517ef7f8-77d0-4eb0-b539-05e03a23f910",
							"517ef7f8-77d0-4eb0-b539-05e03a23f911"
							));
		});
	}
	
}
