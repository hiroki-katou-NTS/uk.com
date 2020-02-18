package nts.uk.ctx.at.schedule.dom.shift.management;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ShiftPalletsOrgRepository {

	public void add(ShiftPalletsOrg shiftPalletsOrg);

	public void update(ShiftPalletsOrg shiftPalletsOrg);

	public void delete(ShiftPalletsOrg shiftPalletsOrg);

	public ShiftPalletsOrg findShiftPalletOrg(int targetUnit, String targetId, int page);
	
	public ShiftPalletsOrg findByTargetAndPge(TargetOrgIdenInfor targetOrgIdenInfor , int page);

}
