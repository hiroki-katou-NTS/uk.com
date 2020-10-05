/**
 * 
 */
package nts.uk.ctx.at.schedule.app.find.schedule.shiftmanagement.shiftwork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * @author laitv
 * Query: ページを指定して組織別シフトパレットを取得する
 *
 */
@Stateless
public class GetShiftPalettebyOrgAndSpePage {
	
	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;
	
	public ShiftPalletsOrg getShiftPalletOrg(int pageNumber, String workPlaceId) {
		Optional<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findShiftPalletOrg(TargetOrganizationUnit.WORKPLACE.value, workPlaceId, pageNumber);
		if (shiftPalletsOrg.isPresent()) {
			return shiftPalletsOrg.get();
		}
		return null;
	}
}
