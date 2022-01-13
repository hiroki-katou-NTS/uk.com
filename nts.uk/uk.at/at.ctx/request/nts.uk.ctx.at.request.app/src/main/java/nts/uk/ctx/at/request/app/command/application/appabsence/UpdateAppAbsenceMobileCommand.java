package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAppAbsenceMobileCommand {
	
	private ApplicationUpdateCmd application;
    
    // 休暇申請
    private ApplyForLeaveDto applyForLeave;
    
    private AppDispInfoStartupCmd appDispInfoStartupOutput;
	
    // 休日の申請日<List>
	private List<String> holidayAppDates;
	
	// 古いの休出代休紐付け管理<List>
	private List<LeaveComDayOffManaDto> leaveComDayOffManaDto;
	
	// 古いの振出振休紐付け管理<List>
	private List<PayoutSubofHDManagementDto> payoutSubofHDManagementDto;
	
	private boolean holidayFlg;
}
