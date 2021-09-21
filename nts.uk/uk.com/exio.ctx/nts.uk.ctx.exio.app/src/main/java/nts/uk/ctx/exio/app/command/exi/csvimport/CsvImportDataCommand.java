package nts.uk.ctx.exio.app.command.exi.csvimport;

import lombok.Data;

@Data
public class CsvImportDataCommand {
	private String processId;
	private int csvLine;
	private int currentLine;
	private int errorCount;
	private String stopMode;
	private String stateBehavior;
	/**
	 * 条件設定コード
	 */
	private String conditionSetCode;
	/**
	 * ファイルID
	 */
	private String csvFileId;
	/**
	 * 文字コード
	 */
	private int endcoding;
}
