package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SettingNo65 {
	//休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	private int pridigCheck;
	//振休管理設定．管理区分
	private boolean subVacaManage;
	//休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	private boolean subVacaTypeUseFlg;
	//代休管理設定．管理区分
	private boolean subHdManage;
	//休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	private boolean subHdTypeUseFlg;
}
