package nts.uk.ctx.at.schedule.app.query.schedule.shift.management.shifttable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicManagementShiftTableDto {
	
	private TargetOrgIdenInforDto targetOrgIdenInfor;
	
	private String endDatePublicationPeriod;
	
	private String optEditStartDate;
	
	public static PublicManagementShiftTableDto toDto(PublicManagementShiftTable domain) {
		return new PublicManagementShiftTableDto(
				TargetOrgIdenInforDto.toDto(domain.getTargetOrgIdenInfor()),
				domain.getEndDatePublicationPeriod().toString(),
				domain.getOptEditStartDate().isPresent() ? domain.getOptEditStartDate().get().toString() : "");
	}

}
