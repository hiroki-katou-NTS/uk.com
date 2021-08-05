package nts.uk.ctx.exio.app.input.command.setting;

import lombok.Getter;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingDto;

@Getter
public class SaveExternalImportSettingCommand {
	
	/** 新規モードかどうか */
	private Boolean isCreateMode;
	
	/** 登録内容 */
	private ExternalImportSettingDto setting;
	
}
