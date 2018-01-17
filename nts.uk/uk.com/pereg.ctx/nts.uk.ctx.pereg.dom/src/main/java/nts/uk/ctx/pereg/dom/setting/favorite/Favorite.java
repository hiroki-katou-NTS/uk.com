/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favorite;

import lombok.Getter;

/**
 * @author danpv
 * @domain お気に入り
 */
@Getter
public class Favorite {
	
	/**
	 * お気に入りID
	 */
	private String favoriteId;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 個人情報カテゴリID
	 */
	private String personInfoCategoryId;
	
	/**
	 * 名称
	 */
	private FavoriteName favoriteName;
	
	/*-----------------------optional --------------------*/
	
	/**
	 * メモ	
	 */
	private String note;
	
	/**
	 * 帳票ID
	 */
	private String formId;
	
	/**
	 * 初期帳票
	 */
	private Form form;

}
