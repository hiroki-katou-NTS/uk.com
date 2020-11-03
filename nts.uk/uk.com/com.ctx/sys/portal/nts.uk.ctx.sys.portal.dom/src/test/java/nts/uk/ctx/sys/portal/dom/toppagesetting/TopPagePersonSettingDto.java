package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Builder;
import lombok.Data;

/**
 * The Class TopPagePersonSetting.
 * Test Dto 個人別トップページ設定
 */
@Data
@Builder
public class TopPagePersonSettingDto implements TopPagePersonSetting.MementoGetter, TopPagePersonSetting.MementoSetter {

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	private String topMenuCode;
	
	private String loginMenuCode;
	
	private int system;
	
	private int menuClassification;
	
	private Integer switchingDate;

}
