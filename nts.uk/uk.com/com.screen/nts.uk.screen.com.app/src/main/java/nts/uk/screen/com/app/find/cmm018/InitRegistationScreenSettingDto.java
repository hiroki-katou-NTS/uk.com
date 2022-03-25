package nts.uk.screen.com.app.find.cmm018;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.request.app.find.setting.request.application.AppUseAtrDto;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ApproverOperationSettingsDto;

@Data
@Builder
public class InitRegistationScreenSettingDto {

	/** List<申請種類> */
	private List<AppUseAtrDto> appUseAtrs;
	
	/** 自分の承認者の運用設定 */
	private ApproverOperationSettingsDto setting;
}
