package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 外部受入カテゴリ
 */
@AllArgsConstructor
@Value
public class ExAcpCategoryDto {

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * カテゴリ名
	 */
	private String categoryName;
	
	/**
	 * 就業システム区分
	 */
	private int atSysFlg;
	/**
	 * 人事システム区分
	 */
	private int persSysFlg;
	/**
	 * 給与システム区分
	 */
	private int salarySysFlg;
	/**
	 * オフィスヘルパー区分
	 */
	private int officeSysFlg;

}
