package nts.uk.ctx.exio.dom.input.setting.source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
