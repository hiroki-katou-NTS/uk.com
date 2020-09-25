package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author hieult
 *
 */
public interface ShiftPalletsComRepository {
	//Add ShiftPalletsCom
	public void add(ShiftPalletsCom shiftPalletsCom);
	//Update ShiftPalletsCom
	public void update(ShiftPalletsCom shiftPalletsCom);
	//Delete ShiftPalletsCom 
	public void delete(ShiftPalletsCom shiftPalletsCom);
	//Find by key ShiftPalletsCom
	public Optional<ShiftPalletsCom> findShiftPallet(String companyId, int page);
	//Find by ID
	public List<ShiftPalletsCom> findShiftPallet(String companyId);
	//Check exist
	boolean isDuplicateRoleSetCd(String companyId, int page, int position);
	//Delete 
	public void deleteByPage(String companyID,int page);

	//[5] 使用するシフトパレットをすべて取得する
	public List<ShiftPalletsCom> findShiftPalletUse(String companyId);
	
	//[6]exists(会社ID, ページ)
	public boolean exists(String companyID,int page);
}
