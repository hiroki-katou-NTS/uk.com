package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;

import lombok.Data;

@Data
public class ScreenDisplayProcessingDto {
	/**
	 * List <パターン設定>
	 */
	private List<DataStoragePatternSettingDto> patterns;
	
	/**
	 * List<システム種類>
	 */
	private List<Integer> systemTypes;
}
