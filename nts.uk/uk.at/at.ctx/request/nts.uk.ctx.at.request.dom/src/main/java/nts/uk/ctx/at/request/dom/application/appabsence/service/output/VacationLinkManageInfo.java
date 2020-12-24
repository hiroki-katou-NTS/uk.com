package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

@Data
@AllArgsConstructor
public class VacationLinkManageInfo {

    private List<LeaveComDayOffManagement> leaveComDayOffManagements;
    
    private List<PayoutSubofHDManagement> payoutSubofHDManagements;
}
