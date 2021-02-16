package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import static mockit.Deencapsulation.invoke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheModifyWorkplaceFunctionCtrlTest {
	
	@Test
	public void testCreateInv1() {
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = 
				Arrays.asList(
						FuncCtrlDisplayPeriod.TwentyEightDayCycle,
						FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = 
				Arrays.asList(FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			ScheModifyWorkplaceFunctionCtrl.create(
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
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = 
				Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = 
				Arrays.asList(
						FuncCtrlDisplayFormat.AbbreviatedName,
						FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			ScheModifyWorkplaceFunctionCtrl.create(
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
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = 
				Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = 
				Arrays.asList(FuncCtrlDisplayFormat.AbbreviatedName);
		List<FuncCtrlStartControl> pageCanBeStarted = 
				Arrays.asList(
					FuncCtrlStartControl.ByDate,
					FuncCtrlStartControl.ByDate);
		
		NtsAssert.systemError(() -> {
			ScheModifyWorkplaceFunctionCtrl.create(
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
		
		List<FuncCtrlDisplayPeriod> useDisplayPeriod = 
				Arrays.asList(FuncCtrlDisplayPeriod.TwentyEightDayCycle);
		List<FuncCtrlDisplayFormat> useDisplayFormat = new ArrayList<>();
		List<FuncCtrlStartControl> pageCanBeStarted = 
				Arrays.asList(FuncCtrlStartControl.ByDate);
		
		NtsAssert.businessException( "Msg_2125" ,() -> {
			ScheModifyWorkplaceFunctionCtrl.create(
					useDisplayPeriod, 
					useDisplayFormat, 
					pageCanBeStarted, 
					NotUseAtr.NOT_USE,
					Optional.empty()
					);
		});
		
	}

}
