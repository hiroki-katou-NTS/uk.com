package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Data;

@Data
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
