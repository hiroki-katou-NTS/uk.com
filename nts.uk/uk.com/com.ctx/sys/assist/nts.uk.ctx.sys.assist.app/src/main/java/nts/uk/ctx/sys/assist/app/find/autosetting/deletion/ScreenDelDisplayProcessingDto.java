package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import java.util.List;

import lombok.Data;

@Data
public class ScreenDelDisplayProcessingDto {
	/**
	 * List <パターン設定>
	 */
	private List<DataDeletionPatternSettingDto> patterns;
	
	/**
	 * List<システム種類>
	 */
	private List<Integer> systemTypes;
}
