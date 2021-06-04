package nts.uk.ctx.exio.dom.input.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.setting.source.ExternalImportCsvFileInfo;

/**
 * 受入設定
 */
@Getter
@AllArgsConstructor
public class ExternalImportSetting extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode code;
	
	/** 受入設定名称 */
	private ExternalImportName name;
	
	/** 受入グループID */
	private int externalImportGroupId;
	
	/** 受入モード */
	private ImportingMode importingMode;
	
	/** CSVファイル情報 */
	private ExternalImportCsvFileInfo csvFileInfo;
	
}
