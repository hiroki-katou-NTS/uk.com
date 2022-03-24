package nts.uk.screen.com.app.find.smm001;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalImportSettingDto {
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
}
