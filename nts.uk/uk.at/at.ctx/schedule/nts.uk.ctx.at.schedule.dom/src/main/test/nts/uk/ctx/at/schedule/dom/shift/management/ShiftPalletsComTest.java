package nts.uk.ctx.at.schedule.dom.shift.management;

import org.junit.Test;

import nts.arc.testing.exception.BusinessExceptionAssert;

public class ShiftPalletsComTest {
	
	@Test
	public void modifyShiftPallets(ShiftPallet shiftPallet){
		ShiftPalletsCom shiftPalletsCom  = this.getShiftPalletsCom();
		
		BusinessExceptionAssert.id("Msg_1586", () -> shiftPalletsCom.modifyShiftPallets(shiftPallet));
	}
	
	public static ShiftPalletsCom getShiftPalletsCom(){
		return null;
	}
}
