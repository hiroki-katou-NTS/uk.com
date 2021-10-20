package nts.uk.ctx.at.request.app.command.application.overtime;


import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalRootStateImportDto;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {
	
	public String companyId;
	
	public AppOverTimeInsertCommand appOverTime;
	
	public AppDispInfoStartupCmd appDispInfoStartupDto;
	
	public Boolean isMail;

	// 申請種類別設定
	public AppTypeSettingCommand appTypeSetting;
	
	/**
	 * List＜社員ID, 承認ルートの内容＞
	 */
	public Map<String, ApprovalRootStateImportDto> approvalRootContentMap;
}
