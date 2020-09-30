package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * UnitTest: 勤務方法の関係性
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class WorkMethodRelationshipTest {
	
	@Test
	public void getters() {
		val workMethodRela = new WorkMethodRelationship(
				new WorkMethodAttendance(new WorkTimeCode("001")), 
				Arrays.asList(new WorkMethodHoliday()),
				RelationshipSpecifiedMethod.ALLOW_SPECIFY_WORK_DAY
				);
		NtsAssert.invokeGetters(workMethodRela);
	}
	
	/**
	 * inv-1: 当日の勤務方法リスト.size() > 0	
	 * ケース:当日の勤務方法リスト.size() = 0 -> Msg_1720
	 */
	@Test
	public void check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1720", () -> {
			WorkMethodRelationship.create(
					new WorkMethodHoliday(), 
					Collections.emptyList(),
					RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY
					);
		});
	}
	
	/**
	 * inv-2: 前日の勤務方法.勤務方法の種類を取得する() != 連続勤務			
	 * ケース: 同前日の勤務方法.勤務方法の種類を取得する() = 連続勤務	 -> Msg_1885
	 */
	@Test
	public void check_inv2_prevWorkMethod_is_Continuous() {
		NtsAssert.businessException("Msg_1877", () -> {
			WorkMethodRelationship.create(
					new WorkMethodContinuousWork(), 
					Arrays.asList(new WorkMethodHoliday()),
					RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY
					);
		});
	}
	
	
	/**
	 * 許可
	 * 勤務方法の関係性を作成する：成功(success)
	 * 
	 */
	@Test
	public void create_WorkMethodRelationship_Allow_success() {
		val workMethodRela = new WorkMethodRelationship(
				new WorkMethodAttendance(new WorkTimeCode("001")), 
				Arrays.asList(new WorkMethodHoliday()),
				RelationshipSpecifiedMethod.ALLOW_SPECIFY_WORK_DAY
				);
		
		assertThat(workMethodRela.getSpecifiedMethod()).isEqualTo(RelationshipSpecifiedMethod.ALLOW_SPECIFY_WORK_DAY);
		assertThat(workMethodRela.getPrevWorkMethod().getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.ATTENDANCE);
		assertThat(workMethodRela.getCurrentWorkMethodList().get(0).getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.HOLIDAY);
	}
	
	/**
	 * 許可しない。
	 * 勤務方法の関係性を作成する：成功(success)
	 * 
	 */
	@Test
	public void create_WorkMethodRelationship_Not_Allow_success() {
		val prevWorkMethod = new WorkMethodAttendance(new WorkTimeCode("YK1")); /**夜勤*/
		/**早朝リスト*/
		List<WorkMethod> currentWorkMethodList = Arrays.asList(
				new WorkMethodAttendance(new WorkTimeCode("SC1")),
			    new WorkMethodAttendance(new WorkTimeCode("SC2")),
			    new WorkMethodAttendance(new WorkTimeCode("SC3")));
		val workMethodRela = new WorkMethodRelationship(
				prevWorkMethod, 
				currentWorkMethodList,
				RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY
				);
		
		assertThat(workMethodRela.getSpecifiedMethod())
				.isEqualTo(RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY);
		assertThat(workMethodRela.getPrevWorkMethod().getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.ATTENDANCE);
		assertThat(workMethodRela.getCurrentWorkMethodList().get(0).getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.ATTENDANCE);
		assertThat(workMethodRela.getCurrentWorkMethodList()).containsExactlyInAnyOrderElementsOf(currentWorkMethodList);
	}
	
	
	/**
	 * 許可しない
	 * 勤務方法の関係性を作成する：成功(success)
	 * 
	 */
	@Test
	public void create_WorkMethodRelationship_Not_Allow_success1() {
		val workMethodRela = new WorkMethodRelationship(
				new WorkMethodHoliday(), 
				Arrays.asList(new WorkMethodHoliday()),
				RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY
				);
		
		assertThat(workMethodRela.getSpecifiedMethod())
				.isEqualTo(RelationshipSpecifiedMethod.NOT_ALLOW_SPECIFY_WORK_DAY);
		assertThat(workMethodRela.getPrevWorkMethod().getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.HOLIDAY);
		assertThat(workMethodRela.getCurrentWorkMethodList().get(0).getWorkMethodClassification())
				.isEqualTo(WorkMethodClassfication.HOLIDAY);
	}

}
