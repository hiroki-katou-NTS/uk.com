package nts.uk.screen.com.app.find.smm001;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 起動のOutput
 *
 */
@Data
@Builder
public class OutputOfStartupDto {
	
	//Smile連携受入設定
	private List<SmileCooperationAcceptanceSettingDto> smileCooperationAcceptanceSettings;
	
	// Map＜条件設定コード、条件設定名称＞
	private List<ExternalImportSettingDto> externalImportSettings;
}
