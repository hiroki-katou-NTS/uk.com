package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

public class DeterEditStatusShiftServiceTest {

	/**
	 * 勤務予定.編集状態.isEmpty 
	 */
	@Test
	public void testToDecide() {
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().isPresent()).as("editState").isFalse();
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 is empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 is empty
	 */
	@Test
	public void testToDecide_1() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(27, EditStateSetting.REFLECT_APPLICATION));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().isPresent()).as("editState").isFalse();
	}
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 is empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態  == EditStateSetting.IMPRINT
	 */
	@Test
	public void testToDecide_2() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().isPresent()).as("editState").isFalse();
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 is empty
	 *  勤務種類状態  == EditStateSetting.IMPRINT
	 */
	@Test
	public void testToDecide_3() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().isPresent()).as("editState").isFalse();
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  勤務種類状態  == EditStateSetting.REFLECT_APPLICATION
	 */
	@Test
	public void testToDecide_4() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.REFLECT_APPLICATION),
				new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.REFLECT_APPLICATION);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態  == EditStateSetting.REFLECT_APPLICATION
	 */
	@Test
	public void testToDecide_5() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(29, EditStateSetting.REFLECT_APPLICATION));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.REFLECT_APPLICATION);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  勤務種類状態  == EditStateSetting.HAND_CORRECTION_OTHER
	 *  就業時間帯状態 != EditStateSetting.REFLECT_APPLICATION
	 */
	@Test
	public void testToDecide_6() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.HAND_CORRECTION_OTHER),
				new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_OTHER);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  勤務種類状態  != EditStateSetting.REFLECT_APPLICATION
	 *  就業時間帯状態 == EditStateSetting.HAND_CORRECTION_OTHER
	 */
	@Test
	public void testToDecide_7() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(29, EditStateSetting.HAND_CORRECTION_OTHER));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_OTHER);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  勤務種類状態  == EditStateSetting.HAND_CORRECTION_MYSELF
	 *  就業時間帯状態 != EditStateSetting.HAND_CORRECTION_OTHER && 就業時間帯状態 != EditStateSetting.REFLECT_APPLICATION 
	 */
	@Test
	public void testToDecide_8() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.HAND_CORRECTION_MYSELF),
				new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_MYSELF);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  勤務種類状態  != EditStateSetting.HAND_CORRECTION_OTHER && 就業時間帯状態 != EditStateSetting.REFLECT_APPLICATION 
	 *  就業時間帯状態 == EditStateSetting.HAND_CORRECTION_MYSELF 
	 */
	@Test
	public void testToDecide_9() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(29, EditStateSetting.HAND_CORRECTION_MYSELF));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_MYSELF);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 is empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  就業時間帯状態 == EditStateSetting.REFLECT_APPLICATION
	 */
	@Test
	public void testToDecide_10() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(29, EditStateSetting.REFLECT_APPLICATION));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.REFLECT_APPLICATION);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 is empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  就業時間帯状態 == EditStateSetting.HAND_CORRECTION_MYSELF
	 */
	@Test
	public void testToDecide_10_1() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(29, EditStateSetting.HAND_CORRECTION_MYSELF));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_MYSELF);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 is empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  就業時間帯状態 == EditStateSetting.HAND_CORRECTION_OTHER
	 */
	@Test
	public void testToDecide_10_2() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(29, EditStateSetting.HAND_CORRECTION_OTHER));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_OTHER);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 is empty
	 *  勤務種類状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  勤務種類状態 == EditStateSetting.REFLECT_APPLICATION
	 */
	@Test
	public void testToDecide_11() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.REFLECT_APPLICATION));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.REFLECT_APPLICATION);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 is empty
	 *  勤務種類状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  勤務種類状態 == EditStateSetting.HAND_CORRECTION_MYSELF
	 */
	@Test
	public void testToDecide_11_1() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.HAND_CORRECTION_MYSELF));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_MYSELF);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 is empty
	 *  勤務種類状態 !=EditStateSetting.IMPRINT && 就業時間帯状態 not empty  
	 *  勤務種類状態 == EditStateSetting.HAND_CORRECTION_OTHER
	 */
	@Test
	public void testToDecide_11_2() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.HAND_CORRECTION_OTHER));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().get()).as("editState").isEqualTo(EditStateSetting.HAND_CORRECTION_OTHER);
	}
	
	/**
	 *  勤務予定.編集状態.is not Empty 
	 *  勤務予定.編集状態：find $.勤怠項目ID == 28 not empty
	 *  勤務予定.編集状態：find $.勤怠項目ID == 29 not empty
	 *  就業時間帯状態  == EditStateSetting.IMPRINT
	 *  勤務種類状態  == EditStateSetting.IMPRINT
	 */
	@Test
	public void testToDecide_12() {
		
		List<EditStateOfDailyAttd> lstEditState = Arrays.asList(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, Optional.empty(),
				lstEditState, Optional.empty(), Optional.empty(), Optional.empty());
		
		ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
		assertThat(shiftEditState.getEmployeeID()).as("employeeID").isEqualTo(shiftEditState.getEmployeeID());
		assertThat(shiftEditState.getDate()).as("date").isEqualTo(GeneralDate.today());
		assertThat(shiftEditState.getOptEditStateOfDailyAttd().isPresent()).as("editState").isFalse();
	}

}
