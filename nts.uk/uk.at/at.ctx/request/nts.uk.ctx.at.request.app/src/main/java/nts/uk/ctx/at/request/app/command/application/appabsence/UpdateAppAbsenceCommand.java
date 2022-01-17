package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

@Data
public class UpdateAppAbsenceCommand {
    
    private ApplicationDto application;
    
    // 休暇申請
    private ApplyForLeaveDto applyForLeave;
    
    private AppDispInfoStartupCmd appDispInfoStartupOutput;
	
    // 休日の申請日<List>
	private List<String> holidayAppDates;
	
	// 休出代休紐付け管理<List>
	private List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	// 振出振休紐付け管理<List>
	private List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
	
	private boolean holidayFlg;
}
