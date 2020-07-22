package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author hieult
 *
 */
public interface ShiftPalletsOrgRepository {
	// Add ShiftPalletsOrg
	public void add(ShiftPalletsOrg shiftPalletsOrg);

	// Update ShiftPalletsOrg
	public void update(ShiftPalletsOrg shiftPalletsOrg);
	
	public void delete(ShiftPalletsOrg shiftPalletsOrg);
	// Find by key
	public Optional<ShiftPalletsOrg> findShiftPalletOrg(int targetUnit, String targetId, int page);

	// ①<<Query>> 会社別シフトパレットの一覧を取得する
	public List<ShiftPalletsOrg> findbyWorkPlaceId(int targetUnit, String workplaceId);

	// Delete with id and page
	public void deleteByWorkPlaceId(String workplaceId, int page);

	List<ShiftPalletsOrg> findByCID(String cid);
	//[6] exists (複製元の対象組織,　複製先の ページ)
	public boolean check(TargetOrgIdenInfor targeOrg,int page);

}
