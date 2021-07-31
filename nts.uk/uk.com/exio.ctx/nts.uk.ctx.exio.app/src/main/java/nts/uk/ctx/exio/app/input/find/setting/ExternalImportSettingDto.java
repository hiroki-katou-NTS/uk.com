package nts.uk.ctx.exio.app.input.find.setting;

import lombok.Value;
import nts.uk.ctx.exio.app.input.find.setting.assembly.ExternalImportAssemblyMethodDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

@Value
public class ExternalImportSettingDto {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
	
	/** 受入グループID */
	private int externalImportGroupId;
	
	/** 受入モード */
	private int importingMode;
	
	/** 組立方法 */
	//private ExternalImportAssemblyMethodDto assembly;
	
	public static ExternalImportSettingDto fromDomain(ExternalImportSetting domain) {
		return new ExternalImportSettingDto(
				domain.getCompanyId(), 
				domain.getCode().toString(), 
				domain.getName().toString(), 
				domain.getExternalImportGroupId(), 
				domain.getImportingMode().value);
	}
}
