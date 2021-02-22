package nts.uk.screen.com.ws.ccg008;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToppageSettingDto {

	/** 会社ID */
	private String cid;

	/** リロード間隔 */
	private Integer reloadInterval;
	
	/** 
	 * The top menu code.
	 * 	トップメニューコード
	 **/
	private String topMenuCode;
	
	/** 
	 * The switching date. 
	 * 	切換日
	 **/
	private Integer switchingDate;
	
	/** 
	 * The system. 
	 *	 システム
	 **/
	private Integer system;
	
	/**
	 * 	メニュー分類
	 */
	private Integer menuClassification;
	
	/** 
	 * The login menu code. 
	 * 	ログインメニューコード 
	 **/
	private String loginMenuCode;
}
