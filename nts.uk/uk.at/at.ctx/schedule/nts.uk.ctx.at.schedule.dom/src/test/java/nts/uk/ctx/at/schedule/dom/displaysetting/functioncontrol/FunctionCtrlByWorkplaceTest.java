package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class FunctionCtrlByWorkplaceTest {
	
	@Test
	public void testGetter() {
		
		CompletionMethodControl completionMethodControl = new CompletionMethodControl(
				FuncCtrlCompletionExecutionMethod.SelectAtRuntime, 
				Arrays.asList(FuncCtrlCompletionMethod.AlarmCheck), 
				Arrays.asList("001", "002"));
		
		FunctionCtrlByWorkplace target = new FunctionCtrlByWorkplace(
				Arrays.asList( FuncCtrlDisplayPeriod.LastDayUtil ), 
				Arrays.asList( FuncCtrlDisplayFormat.Shift ), 
				Arrays.asList( FuncCtrlStartControl.ByDate ), 
				NotUseAtr.USE,
				Optional.of(completionMethodControl) );
		
		NtsAssert.invokeGetters( target );
	}
	
	@Test
	public void testCreateInv1() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(
						FuncCtrlDisplayPeriod.TwentyEightDayCycle,
						FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = Arrays.asList(FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.NOT_USE,
					Optional.empty()
					);
		});
		
	}
	
	@Test
	public void testCreateInv2() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = Arrays.asList(
						FuncCtrlDisplayFormat.AbbreviatedName,
						FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.NOT_USE,
					Optional.empty()
					);
		});
		
	}
	
	@Test
	public void testCreateInv3() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = Arrays.asList(FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(
					FuncCtrlStartControl.ByDate,
					FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.NOT_USE,
					Optional.empty()
					);
		});
		
	}
	
	@Test
	public void testCreateInv4() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = new ArrayList<>();
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.businessException( "Msg_1690" ,() -> {
			FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.NOT_USE,
					Optional.empty()
					);
		});
		
	}
	
	@Test
	public void testCreateInv5() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = Arrays.asList(FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.businessException( "Msg_1690" ,() -> {
			FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.USE,
					Optional.empty() );
		});
		
	}
	
	@Test
	public void testCreate_successfully() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = Arrays.asList(
				FuncCtrlDisplayPeriod.TwentyEightDayCycle,
				FuncCtrlDisplayPeriod.LastDayUtil );
		List<FuncCtrlDisplayFormat> useDisplayFormat = Arrays.asList(
				FuncCtrlDisplayFormat.AbbreviatedName,
				FuncCtrlDisplayFormat.Shift);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		CompletionMethodControl completionMethodControl = new CompletionMethodControl(
				FuncCtrlCompletionExecutionMethod.SelectAtRuntime, 
				Arrays.asList(FuncCtrlCompletionMethod.AlarmCheck), 
				Arrays.asList("001", "002"));
		
		// execute
		FunctionCtrlByWorkplace result = FunctionCtrlByWorkplace.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.USE,
					Optional.of(completionMethodControl)
					);
		
		// assert
		assertThat( result.getUseDisplayPeriod() ).containsExactly(
				FuncCtrlDisplayPeriod.TwentyEightDayCycle, 
				FuncCtrlDisplayPeriod.LastDayUtil);
		assertThat( result.getUseDisplayFormat() ).containsExactly(
				FuncCtrlDisplayFormat.AbbreviatedName,
				FuncCtrlDisplayFormat.Shift);
		assertThat( result.getPageCanBeStarted() ).containsExactly( FuncCtrlStartControl.ByDate );
		assertThat( result.getUseCompletionAtr() ).isEqualTo( NotUseAtr.USE );
		assertThat( result.getCompletionMethodControl().get() ).isEqualTo( completionMethodControl );
		
	}

}
