package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;

/**
 * 起動パラメータ DTO
 */
@Data
public class StartupParameterDto<X, Y> {
	
	/**
	 * List<カテゴリ>
	 */
	private List<X> categories;
	
	/**
	 * List<パターン設定>
	 */
	private List<Y> patterns;
	
	/**
	 * ログイン者が担当者か判断する
	 */
	private LoginPersonInCharge pic;
}
