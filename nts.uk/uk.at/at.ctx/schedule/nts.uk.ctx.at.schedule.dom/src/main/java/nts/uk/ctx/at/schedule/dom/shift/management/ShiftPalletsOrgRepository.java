package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;

public interface ShiftPalletsOrgRepository {

	public void add(ShiftPalletsOrg shiftPalletsOrg);

	public void update(ShiftPalletsOrg shiftPalletsOrg);

	public void delete(ShiftPalletsOrg shiftPalletsOrg);

	public Optional<ShiftPalletsOrg> findShiftPalletOrg(int targetUnit, String targetId, int page);
	
	public List<ShiftPalletsOrg> findbyWorkPlaceId(int targetUnit, String workplaceId); 
	
	public void deleteByWorkPlaceId(String workplaceId, int page);

}
