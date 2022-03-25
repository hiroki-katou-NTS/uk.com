package nts.uk.screen.com.app.cmf.cmf001.e.save;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingDto;

@Getter
public class Cmf001eSaveCommand {

	/** 新規モード=true, 更新=false */
	private boolean createMode;
	
	/** 登録内容 */
	private ExternalImportSettingDto setting;

	public ExternalImportCode getCode() {
		return new ExternalImportCode(this.setting.getCode());
	}

	public boolean isNew() {
		return this.createMode;
	}

	public ExternalImportSetting toDomain(Optional<ExternalImportSetting> oldOpt) {
		
		if (Strings.isBlank(setting.getCsvFileId())) {
			throw new BusinessException(new RawErrorMessage("サンプルCSVファイルを指定してください。"));
		}
		
		if (!oldOpt.isPresent()) {
			return this.getSetting().toDomainAsCsvBase();
		}
		
		
		val old = oldOpt.get();
		
		old.setName(new ExternalImportName(setting.getName()));
		old.setCsvFileInfo(setting.toCsvFileInfo());
		
		return old;
	}
}
