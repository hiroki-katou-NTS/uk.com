package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@Data
@AllArgsConstructor
public class CheckTyingManagementParam {

    public WorkTypeDto wtBefore;
    public WorkTypeDto wtAfter;
    public List<LeaveComDayOffManaDto> leaveComDayOffMana;
    public List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
}
