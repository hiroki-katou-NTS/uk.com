package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@NoArgsConstructor
@Getter
@Setter
public class ChangeWokTypeParam {

	public WorkTypeDto workTypeBefore;
	
	public WorkTypeDto workTypeAfter;
	
	public String workTimeCode;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	public List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
	
}
