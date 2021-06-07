package nts.uk.ctx.exio.dom.input.setting.source;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalImportCsvFileInfo {
	
	/** CSVファイルのファイルID */
	private String fileId;
	
	/** 文字コード */
	private ExternalImportCharset charset;
	
	/** CSVの項目名取得行 */
	private ExternalImportRawNumber itemNameRawNumber;
	
	/** CSVの取込開始行 */
	private ExternalImportRawNumber importStartRawNumber;
	
}
