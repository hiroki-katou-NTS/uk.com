package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class CompletionMethodControlTest {
	
	@Test
	public void testCreate_inv2() {
		
		List<FuncCtrlCompletionMethod> completionMethodControl = Arrays.asList(
				FuncCtrlCompletionMethod.Confirm,
				FuncCtrlCompletionMethod.Confirm);
		
		NtsAssert.systemError( () -> {
			
			CompletionMethodControl.create(
					FuncCtrlCompletionExecutionMethod.SelectAtRuntime, 
					completionMethodControl, 
					Collections.emptyList());
		});
	}
	
	@Test
	public void testCreate_inv1() {
		
		NtsAssert.businessException( "Msg_1690" , () -> {
			
			CompletionMethodControl.create(
					FuncCtrlCompletionExecutionMethod.SettingBefore, 
					Collections.emptyList(), 
					Collections.emptyList());
		});
	}
	
	@Test
	public void testCreate_inv3() {
		
		NtsAssert.businessException( "Msg_1690" , () -> {
			
			CompletionMethodControl.create(
					FuncCtrlCompletionExecutionMethod.SettingBefore, 
					Arrays.asList( FuncCtrlCompletionMethod.AlarmCheck ), 
					Collections.emptyList() );
		});
	}
	
	@Test
	public void testCreate_successfully() {
		
		CompletionMethodControl result = CompletionMethodControl.create(
					FuncCtrlCompletionExecutionMethod.SettingBefore, 
					Arrays.asList( FuncCtrlCompletionMethod.AlarmCheck ), 
					Arrays.asList( "001") );
		
		assertThat( result.getCompletionExecutionMethod() ).isEqualTo( FuncCtrlCompletionExecutionMethod.SettingBefore );
		assertThat( result.getCompletionMethodControl() ).containsExactly( FuncCtrlCompletionMethod.AlarmCheck );
		assertThat( result.getAlarmCheckCodeList() ).containsExactly( "001" );
		
	}
	
	@Test
	public void testGetter() {
		
		CompletionMethodControl target = CompletionMethodControl.create(
					FuncCtrlCompletionExecutionMethod.SettingBefore, 
					Arrays.asList( FuncCtrlCompletionMethod.AlarmCheck ), 
					Arrays.asList( "001") );
		
		NtsAssert.invokeGetters(target);
	}

}
