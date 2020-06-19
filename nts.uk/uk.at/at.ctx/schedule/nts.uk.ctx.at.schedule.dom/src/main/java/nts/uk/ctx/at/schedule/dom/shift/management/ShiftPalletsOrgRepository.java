package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;

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

}
