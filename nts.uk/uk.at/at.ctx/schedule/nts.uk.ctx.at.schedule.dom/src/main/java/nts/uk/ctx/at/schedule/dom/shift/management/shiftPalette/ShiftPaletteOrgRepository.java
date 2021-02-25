package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author hieult
 *
 */
public interface ShiftPaletteOrgRepository {
	// Add ShiftPalletsOrg
	public void add(ShiftPaletteOrg shiftPalletsOrg);

	// Update ShiftPalletsOrg
	public void update(ShiftPaletteOrg shiftPalletsOrg);
	
	public void delete(ShiftPaletteOrg shiftPalletsOrg);
	// Find by key
	public Optional<ShiftPaletteOrg> findShiftPalletOrg(int targetUnit, String targetId, int page);

	public List<ShiftPaletteOrg> findbyWorkPlaceId(int targetUnit, String workplaceId);

	// Delete with id and page
	public void deleteByWorkPlaceId(String workplaceId, int page);

	List<ShiftPaletteOrg> findByCID(String cid);
	//[6] exists (複製元の対象組織,　複製先の ページ)
	public boolean exists(TargetOrgIdenInfor targeOrg,int page);
	//[3] Delete(対象組織, ページ)																							
	public void delete(TargetOrgIdenInfor targeOrg,int page);

	List<ShiftPaletteOrg> findbyWorkPlaceIdUse(int targetUnit, String workplaceId);

}
