package nts.uk.ctx.exio.dom.input.setting.source;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.mapping.ExternalImportItemMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalImportCsvAnalysis extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** CSVファイル情報 */
	private ExternalImportCsvFileInfo csvFileInfo;
	
	/** 項目マッピング */
	private List<ExternalImportItemMapping> mapping;
}
