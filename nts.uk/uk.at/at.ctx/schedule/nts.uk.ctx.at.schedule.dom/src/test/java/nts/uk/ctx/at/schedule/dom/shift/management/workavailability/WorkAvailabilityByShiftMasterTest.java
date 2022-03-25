package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
@RunWith(JMockit.class)
public class WorkAvailabilityByShiftMasterTest {
	
	@Injectable
    private WorkAvailability.Require require;
	
	@Test
	public void getters() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		NtsAssert.invokeGetters(shiftExp);
	}
	
	@Test
	public void testGetAssignmentMethod() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		
		assertThat(shiftExp.getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
	}
	
	/**
	 * input: 全部シフトマスタの出勤系か　: false
	 * output: false
	 */
	@Test
	
	public void testIsHolidayAvailability_false() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster1 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		new Expectations(shiftMaster1, shiftMaster2) {
			{
				@SuppressWarnings("unchecked")
				val shiftMasterCodes = (List<ShiftMasterCode>) any;
				
				require.getShiftMaster(shiftMasterCodes);
				result = Arrays.asList(shiftMaster1, shiftMaster2);
				
				shiftMaster1.isAttendanceRate((WorkInformation.Require)any, anyString);
				result = true;
				
				shiftMaster2.isAttendanceRate((WorkInformation.Require)any, anyString);
				result = true;
			}
		};
		
		assertThat(shiftExp.isHolidayAvailability(require, "cid")).isFalse();
	}
	
	/**
	 * input: シフトマスタの出勤系か中に 一つの値:true
	 * output: false
	 */
	@Test
	public void testIsHolidayAvailability_have_element_true() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster1 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		new Expectations(shiftMaster1, shiftMaster2) {
			{
				@SuppressWarnings("unchecked")
				val shiftMasterCodes = (List<ShiftMasterCode>) any;
				
				require.getShiftMaster(shiftMasterCodes);
				result= Arrays.asList(shiftMaster1, shiftMaster2);
				
				shiftMaster1.isAttendanceRate((WorkInformation.Require)any, anyString);
				result = true;
				
				shiftMaster2.isAttendanceRate((WorkInformation.Require)any, anyString);
				result = false;
			}
		};
		
		assertThat(shiftExp.isHolidayAvailability(require, "cid")).isTrue();
	}
	
	/**
	 * input: シフトマスタの出勤系か中にelement first　: true
	 * output: true
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testIsHolidayAvailability_first_true() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster1 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		new Expectations(shiftMaster1, shiftMaster2) {
			{
				require.getShiftMaster((List<ShiftMasterCode>) any);
				result= Arrays.asList(shiftMaster1, shiftMaster2);
				
				shiftMaster1.isAttendanceRate(require, anyString);
				result = false;
			}
		};
		
		assertThat(shiftExp.isHolidayAvailability(require, "cid")).isTrue();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_empty() {
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	// result = empty
            }
        };
        
        boolean result = shiftExp.isMatchingWorkAvailability(require, "cid", workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
		
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_notMatch() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCode("S03");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftExp.isMatchingWorkAvailability(require, "cid", workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
	}

	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_Match() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		WorkAvailabilityByShiftMaster shiftAvailability = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCode("S02");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftAvailability.isMatchingWorkAvailability(require, "cid", workInformation, new ArrayList<>());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02", "S03");
		ShiftMaster shiftMaster1 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		new Expectations() {
            {
            	require.getShiftMaster(Arrays.asList(new ShiftMasterCode("S01")));
            	result = Arrays.asList(shiftMaster1);
            	
            	require.getShiftMaster(Arrays.asList(new ShiftMasterCode("S02")));
            	result = Arrays.asList(shiftMaster2);
            	
            	require.getShiftMaster(Arrays.asList(new ShiftMasterCode("S03")));
            }
        };
		
		WorkAvailabilityDisplayInfo displayInfo = shiftExp.getDisplayInformation(require);
		
		assertThat(displayInfo.getMethod()).isEqualTo(AssignmentMethod.SHIFT);
		assertThat(displayInfo.getShiftList().entrySet())
				.extracting(d -> d.getKey().v(), d -> d.getValue().isPresent() == true? d.getValue().get(): "")
				.contains( tuple("S01", "S01-name")
						 , tuple("S02", "S02-name")
						 , tuple("S03", ""));
		assertThat(displayInfo.getTimeZoneList()).isEmpty();
		
	}
	
	/**
	 * input: シフトマスタコード　= empty
	 * output: systemError
	 */
	
	@Test
	public void create_throw_empty() {
		
		NtsAssert.systemError(() -> {
			WorkAvailabilityByShiftMaster.create(require, Collections.emptyList());
		});
		
	}
	
	/**
	 * input: [S01, S02]
	 * S01 = false
	 * output: Msg_1705
	 */
	@Test
	public void create_throw_Msg_1705_first_false() {
		
		List<ShiftMasterCode> shiftMasterCodes = Arrays.asList(new ShiftMasterCode("S01"), new ShiftMasterCode("S02"));

		new Expectations() {
			{
				require.shiftMasterIsExist(shiftMasterCodes.get(0));
				result = false;
			}
		};

		NtsAssert.businessException("Msg_1705",
				() -> WorkAvailabilityByShiftMaster.create(require, shiftMasterCodes));
	}
	
	/**
	 * input: [S01, S02]
	 * S01 = true, S02 = false
	 * output: Msg_1705
	 */
	@Test
	public void create_throw_Msg_1705_have_element_false() {
		
		List<ShiftMasterCode> shiftMasterCodes = Arrays.asList(new ShiftMasterCode("S01"), new ShiftMasterCode("S02"));

		new Expectations() {
			{
				require.shiftMasterIsExist(shiftMasterCodes.get(0));
				result = true;
				
				require.shiftMasterIsExist(shiftMasterCodes.get(1));
				result = false;
			}
		};

		NtsAssert.businessException("Msg_1705",
				() -> WorkAvailabilityByShiftMaster.create(require, shiftMasterCodes));
	}
	
	/**
	 * input: [S01, S02]
	 * S01 = true, S02 = true
	 * output: create success
	 */
	@Test
	public void create_success() {
		
		List<ShiftMasterCode> shiftMasterCodes = Arrays.asList(new ShiftMasterCode("S01"), new ShiftMasterCode("S02"));
		
		new Expectations() {
			{
				require.shiftMasterIsExist(shiftMasterCodes.get(0));
				result = true;
				
				require.shiftMasterIsExist(shiftMasterCodes.get(1));
				result = true;
			}
		};
		
		val result = WorkAvailabilityByShiftMaster.create(require, shiftMasterCodes);
		assertThat(result.getWorkableShiftCodeList()).extracting(d -> d.v()).containsExactly("S01","S02");
		
	}
}
