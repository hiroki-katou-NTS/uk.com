package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HdShipmentMBTimeZoneParam {
	
	private boolean isChangeWorkType;
	
	private WorkTypeDto workTypeOld;
	
	private WorkTypeDto workTypeNew;
	
	private String workTimeCD;
	
	private List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	private List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
}
