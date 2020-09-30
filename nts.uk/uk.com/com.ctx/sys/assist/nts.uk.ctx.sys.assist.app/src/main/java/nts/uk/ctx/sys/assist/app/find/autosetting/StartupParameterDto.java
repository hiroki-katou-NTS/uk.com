package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;

/**
 * 起動パラメータ DTO
 */
@Data
public class StartupParameterDto {
	
	/**
	 * List<カテゴリ>
	 */
	private List<CategoryDto> categories;
	
	/**
	 * List<パターン設定>
	 */
	private List<DataStoragePatternSetting> patterns;
	
	/**
	 * ログイン者が担当者か判断する
	 */
	private LoginPersonInCharge pic;
}
