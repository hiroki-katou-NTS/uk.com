package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author hieult
 *
 */
public interface ShiftPaletteComRepository {
	//Add ShiftPalletsCom
	public void add(ShiftPaletteCom shiftPalletsCom);
	//Update ShiftPalletsCom
	public void update(ShiftPaletteCom shiftPalletsCom);
	//Delete ShiftPalletsCom 
	public void delete(ShiftPaletteCom shiftPalletsCom);
	//Find by key ShiftPalletsCom
	public Optional<ShiftPaletteCom> findShiftPallet(String companyId, int page);
	//Find by ID
	public List<ShiftPaletteCom> findShiftPallet(String companyId);
	//Check exist
	boolean isDuplicateRoleSetCd(String companyId, int page, int position);
	//Delete 
	public void deleteByPage(String companyID,int page);

	//[5] 使用するシフトパレットをすべて取得する
	public List<ShiftPaletteCom> findShiftPalletUse(String companyId);
	
	//[6]exists(会社ID, ページ)
	public boolean exists(String companyID,int page);
}
