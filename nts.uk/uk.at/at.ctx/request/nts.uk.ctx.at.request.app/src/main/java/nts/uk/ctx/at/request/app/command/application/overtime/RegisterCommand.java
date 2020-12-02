package nts.uk.ctx.at.request.app.command.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {
	
	public String companyId;
	
	public AppOverTimeInsertCommand appOverTime;
	
	public AppDispInfoStartupDto appDispInfoStartupDto;
	
	public Boolean isMail;

	// 申請種類別設定
	public AppTypeSettingCommand appTypeSetting;
}
