package nts.uk.screen.com.app.cmf.cmf001.b.save;

import lombok.Getter;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingDto;

@Getter
public class Cmf001bSaveCommand {
	
	/** 新規モードかどうか */
	private Boolean isCreateMode;
	
	/** 登録内容 */
	private ExternalImportSettingDto setting;
	
}
