package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class WorkCycleRefSettingTest {
	
	@Test
	public void testCreate_Msg_2193() {
		
		NtsAssert.businessException("Msg_2193", () -> {
			
			WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK), 
					0, 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty());
		});
		
	}
	
	@Test
	public void testCreate_Msg_2194() {
		
		NtsAssert.businessException("Msg_2194", () -> {
			
			WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK), 
					0, 
					Optional.of(new WorkTypeCode("wt-001")), 
					Optional.empty(), 
					Optional.empty());
		});
		
	}
	
	@Test
	public void testCreate_Msg_2195() {
		
		NtsAssert.businessException("Msg_2195", () -> {
			
			WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY), 
					0, 
					Optional.empty(),
					Optional.empty(), 
					Optional.empty());
		});
		
	}
	
	@Test
	public void testCreate_ok_not_Msg_2193_and_Msg_2194() {
			
			val setting = WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK), 
					3, 
					Optional.of(new WorkTypeCode("wt-001")),
					Optional.of(new WorkTypeCode("wt-002")), 
					Optional.empty());
			
			assertThat(setting.getWorkCycleCode()).isEqualTo(new WorkCycleCode("wc-code"));
			assertThat(setting.getRefOrder()).containsExactly(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK);
			assertThat(setting.getNumOfSlideDays()).isEqualTo(3);
			assertThat(setting.getLegalHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-001"));
			assertThat(setting.getNonStatutoryHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-002"));
			assertThat(setting.getHolidayCd()).isEmpty();
	}
	
	@Test
	public void testCreate_ok_not_Msg_2195() {
			
			val setting = WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY), 
					3, 
					Optional.empty(),
					Optional.empty(),
					Optional.of(new WorkTypeCode("wt-003")));
			
			assertThat(setting.getWorkCycleCode()).isEqualTo(new WorkCycleCode("wc-code"));
			assertThat(setting.getRefOrder()).containsExactly(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY);
			assertThat(setting.getNumOfSlideDays()).isEqualTo(3);
			assertThat(setting.getLegalHolidayCd()).isEmpty();
			assertThat(setting.getNonStatutoryHolidayCd()).isEmpty();
			assertThat(setting.getHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-003"));
	}
	
	@Test
	public void testCreate_ok_full() {
			
			val setting = WorkCycleRefSetting.create(
					new WorkCycleCode("wc-code"),
					Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.PUB_HOLIDAY), 
					3, 
					Optional.of(new WorkTypeCode("wt-001")),
					Optional.of(new WorkTypeCode("wt-002")), 
					Optional.of(new WorkTypeCode("wt-003")));
			
			assertThat(setting.getWorkCycleCode()).isEqualTo(new WorkCycleCode("wc-code"));
			assertThat(setting.getRefOrder()).containsExactly(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.PUB_HOLIDAY);
			assertThat(setting.getNumOfSlideDays()).isEqualTo(3);
			assertThat(setting.getLegalHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-001"));
			assertThat(setting.getNonStatutoryHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-002"));
			assertThat(setting.getHolidayCd().get()).isEqualTo(new WorkTypeCode("wt-003"));
	}

}
