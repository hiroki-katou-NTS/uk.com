package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;

/**
 * データ保存のパターン設定DTO
 */
@Data
@AllArgsConstructor
public class SaveSetDto {
	/**
	 * 保存セットコード
	 */
	private String patternCode;
	
	/**
	 * 保存名称
	 */
	private String saveName;
	
	public static SaveSetDto fromDomain(DataRecoveryResult domain) {
		return new SaveSetDto(domain.getPatternCode(), domain.getSaveName().v());
	}
}
