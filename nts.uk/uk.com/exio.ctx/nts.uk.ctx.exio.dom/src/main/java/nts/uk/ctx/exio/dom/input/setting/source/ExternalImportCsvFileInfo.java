package nts.uk.ctx.exio.dom.input.setting.source;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CSVファイル情報
 */
@Getter
@AllArgsConstructor
public class ExternalImportCsvFileInfo {
	
	/** CSVの項目名取得行 */
	private ExternalImportRawNumber itemNameRawNumber;
	
	/** CSVの取込開始行 */
	private ExternalImportRawNumber importStartRawNumber;
}
