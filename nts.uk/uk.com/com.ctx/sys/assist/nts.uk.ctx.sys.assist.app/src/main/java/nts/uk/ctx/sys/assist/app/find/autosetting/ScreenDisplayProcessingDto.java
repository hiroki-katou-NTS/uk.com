package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;

@Data
public class ScreenDisplayProcessingDto {
	/**
	 * List <パターン設定>
	 */
	private List<DataStoragePatternSetting> patterns;
	
	/**
	 * List<システム種類>
	 */
	private List<Integer> systemTypes;
}
