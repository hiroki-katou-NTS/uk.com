package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettings;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopPageSettingDto {
	/** 
	 * The top menu code.
	 * 	トップメニューコード
	 **/
	private String topMenuCode;

	/** 
	 * The system. 
	 *	 システム
	 **/
	private int system;
	
	/**
	 * 	メニュー分類
	 */
	private int menuClassification;
	
	/** 
	 * The login menu code. 
	 * 	ログインメニューコード 
	 **/
	private String loginMenuCode;
	
	/** 
	 * The switching date. 
	 * 	切換日
	 **/
	private int switchingDate;
	
	public static TopPageSettingDto fromDomain(TopPageSettings domain) {
		return TopPageSettingDto.builder()
				.loginMenuCode(domain.getMenuLogin().getLoginMenuCode().v())
				.menuClassification(domain.getMenuLogin().getMenuClassification().value)
				.system(domain.getMenuLogin().getSystem().value)
				.switchingDate(domain.getSwitchingDate().v())
				.topMenuCode(domain.getTopMenuCode().v())
				.build();
	}
}
