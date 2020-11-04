package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Builder;
import lombok.Data;

/**
 * The Class TopPageRoleSetting.
 * Test Dto 権限別トップページ設定
 */
@Data
@Builder
public class TopPageRoleSettingDto implements TopPageRoleSetting.MementoGetter, TopPageRoleSetting.MementoSetter {

	private String companyId;
	
	private String roleSetCode;
	
	private String topMenuCode;
	
	private String loginMenuCode;
	
	private int system;
	
	private int menuClassification;
	
	private Integer switchingDate;

}
