package nts.uk.ctx.at.schedule.app.query.schedule.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTableRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
public class GetPublicInforScreen {

	@Inject
	private PublicManagementShiftTableRepository repository;
	
	public PublicManagementShiftTableDto get(TargetOrgIdenInfor targetOrgIdenInfor) {
		
		Optional<PublicManagementShiftTable> publicManagementShiftTable = repository.get(targetOrgIdenInfor);
		
		if (!publicManagementShiftTable.isPresent()) {
			return new PublicManagementShiftTableDto();
		}
		
		return PublicManagementShiftTableDto.toDto(publicManagementShiftTable.get());
	}
}
