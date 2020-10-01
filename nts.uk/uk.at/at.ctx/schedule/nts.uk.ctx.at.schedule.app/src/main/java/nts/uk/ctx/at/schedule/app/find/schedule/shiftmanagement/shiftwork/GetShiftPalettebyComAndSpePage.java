/**
 * 
 */
package nts.uk.ctx.at.schedule.app.find.schedule.shiftmanagement.shiftwork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * Query: ページを指定して会社別シフトパレットを取得する
 *
 */
@Stateless
public class GetShiftPalettebyComAndSpePage {
	
	@Inject
	private ShiftPalletsComRepository shiftPalletsComRepo;
	
	public ShiftPalletsCom getShiftPalletCom(int pageNumber) {
		String companyId = AppContexts.user().companyId();
		Optional<ShiftPalletsCom> shiftPalletsCom = shiftPalletsComRepo.findShiftPallet(companyId, pageNumber);
		if (shiftPalletsCom.isPresent()) {
			return shiftPalletsCom.get();
		}
		return null;
	}
}
