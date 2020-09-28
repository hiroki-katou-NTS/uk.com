package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.Value;

@Value
public class FindDataHistoryDto {
	/**
	 * 保存セットコード
	 */
	private String patternCode;
	
	/**
	 * 保存名称
	 */
	private String saveName;
}
