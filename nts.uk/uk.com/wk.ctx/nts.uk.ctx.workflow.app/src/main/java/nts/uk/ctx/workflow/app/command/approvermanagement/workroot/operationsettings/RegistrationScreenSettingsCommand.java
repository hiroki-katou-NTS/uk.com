package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.List;

import lombok.Getter;

@Getter
public class RegistrationScreenSettingsCommand {

	/** 利用するレベル */
	private int approvalLevelNo;
	
	/** 利用する種類設定<List> */
	private List<SettingTypeUsedDto> settingTypeUseds;
	
	/** 自分の承認者設定画面に表示情報 */
	private ApproverSettingScreenInforDto approverSettingScreenInfor;
	
}
