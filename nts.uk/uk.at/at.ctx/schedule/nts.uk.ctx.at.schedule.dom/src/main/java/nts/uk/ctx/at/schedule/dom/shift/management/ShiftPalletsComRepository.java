package nts.uk.ctx.at.schedule.dom.shift.management;

public interface ShiftPalletsComRepository {
	
	public void add(ShiftPalletsCom shiftPalletsCom);
	
	public void update(ShiftPalletsCom shiftPalletsCom);
	
	public void delete(ShiftPalletsCom shiftPalletsCom);
	
	public ShiftPalletsCom findShiftPallet(String companyId, int page);

	boolean isDuplicateRoleSetCd(String companyId, int page, int position);

}
