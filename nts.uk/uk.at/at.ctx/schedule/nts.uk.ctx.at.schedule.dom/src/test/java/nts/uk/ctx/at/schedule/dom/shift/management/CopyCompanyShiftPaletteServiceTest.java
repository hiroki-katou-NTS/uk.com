package nts.uk.ctx.at.schedule.dom.shift.management;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.CopyCompanyShiftPaletteService;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.CopyCompanyShiftPaletteService.Require;

@RunWith(JMockit.class)
public class CopyCompanyShiftPaletteServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * require.会社別シフトパレットを存在するか(複製元のシフトパレット.会社ID, 複製先のページ) == true
	 * 上書きするか = false
	 */
	@Test
	public void testDuplicate_throw_1712() {
		ShiftPaletteCom shiftPalletsCom = ShiftPalletsComHelper.DUMMY;
		int page = 2;
		ShiftPaletteName shiftPalletName = new ShiftPaletteName("shiftPalletName");
		boolean overwrite = false;
		
		new Expectations() {
			{
				require.exists(shiftPalletsCom.getCompanyId(), page);
				result = true;
			}
		};
		NtsAssert.businessException("Msg_1712", () -> {
			CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom, page, shiftPalletName,
					overwrite);});
	}
	/**
	 * require.会社別シフトパレットを存在するか(複製元のシフトパレット.会社ID, 複製先のページ) true
	 * 上書きするか = true;
	 */
	@Test
	public void testDuplicate() {
		ShiftPaletteCom shiftPalletsCom = ShiftPalletsComHelper.DUMMY;
		int page = 2;
		ShiftPaletteName shiftPalletName = new ShiftPaletteName("shiftPalletName");
		boolean overwrite = true;
		
		new Expectations() {
			{
				require.exists(shiftPalletsCom.getCompanyId(), page);
				result = true;
			}
		};
		NtsAssert.atomTask(() -> CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom, page, shiftPalletName,
					overwrite),
				any -> require.deleteByPage(shiftPalletsCom.getCompanyId(), page),
				any -> require.add(any.get())
		);
	}
	
	/**
	 * require.会社別シフトパレットを存在するか(複製元のシフトパレット.会社ID, 複製先のページ) false
	 * 上書きするか = true;
	 */
	@Test
	public void testDuplicate_2() {
		ShiftPaletteCom shiftPalletsCom = ShiftPalletsComHelper.DUMMY;
		int page = 2;
		ShiftPaletteName shiftPalletName = new ShiftPaletteName("shiftPalletName");
		boolean overwrite = true;
		new Expectations() {
			{
				require.exists(shiftPalletsCom.getCompanyId(), page);
				result = false;
			}
		};
		NtsAssert.atomTask(() -> CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom, page, shiftPalletName,
				overwrite),
				any -> require.add(any.get())
		);
	}
	
	@Test
	public void testDuplicate_3() {
		ShiftPaletteCom shiftPalletsCom = ShiftPalletsComHelper.DUMMY;
		int page = 2;
		ShiftPaletteName shiftPalletName = new ShiftPaletteName("shiftPalletName");
		boolean overwrite = false;
		new Expectations() {
			{
				require.exists(shiftPalletsCom.getCompanyId(), page);
				result = false;
			}
		};
		NtsAssert.atomTask(() -> CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom, page, shiftPalletName,
				overwrite),
				any -> require.add(any.get())
		);
	}

}
