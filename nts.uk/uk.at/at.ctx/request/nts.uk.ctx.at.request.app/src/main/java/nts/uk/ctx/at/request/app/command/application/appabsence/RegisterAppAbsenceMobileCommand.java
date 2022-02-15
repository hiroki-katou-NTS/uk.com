package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterAppAbsenceMobileCommand {

    // 休暇申請
    private ApplyForLeaveDto applyForLeave;
    
    // 休日の申請日<List>
    private List<String> appDates;
    
    // 休出代休紐付け管理<List>
    private List<LeaveComDayOffManaDto> leaveComDayOffMana;
    
    // 振出振休紐付け管理<List>
    private List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
    
    // メールサーバ設定済区分
    private boolean mailServerSet;
    
    // 承認ルートインスタンス
    private List<ApprovalPhaseStateForAppDto> approvalRoot;
    
    private ApplicationInsertCmd application;
    
    private AppTypeSettingCommand apptypeSetting;
    
    private boolean holidayFlg;
}
