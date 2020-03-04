package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;

public interface ShiftPalletsComRepository {
	
	public void add(ShiftPalletsCom shiftPalletsCom);
	
	public void update(ShiftPalletsCom shiftPalletsCom);
	
	public void delete(ShiftPalletsCom shiftPalletsCom);
	
	public Optional<ShiftPalletsCom> findShiftPallet(String companyId, int page);
	
	public List<ShiftPalletsCom> findShiftPallet(String companyId);

	boolean isDuplicateRoleSetCd(String companyId, int page, int position);
	
	public void deleteByPage(String companyID,int page);
}
