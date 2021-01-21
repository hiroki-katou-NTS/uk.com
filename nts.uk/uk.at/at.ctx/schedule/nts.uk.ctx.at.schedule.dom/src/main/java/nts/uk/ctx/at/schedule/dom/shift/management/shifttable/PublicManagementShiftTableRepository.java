package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface PublicManagementShiftTableRepository {

	//[1] Insert(シフト表の公開管理)
	void insert(PublicManagementShiftTable shiftTable);
	
	//[2] Update(シフト表の公開管理)
	void update(PublicManagementShiftTable shiftTable);
	
	//[3] get
	Optional<PublicManagementShiftTable> get(TargetOrgIdenInfor idenInfor);

}
