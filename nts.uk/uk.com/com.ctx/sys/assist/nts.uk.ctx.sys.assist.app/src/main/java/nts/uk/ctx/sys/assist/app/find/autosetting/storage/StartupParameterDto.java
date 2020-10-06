package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;

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
	private List<DataStoragePatternSettingDto> patterns;
	
	/**
	 * ログイン者が担当者か判断する
	 */
	private LoginPersonInCharge pic;
}
