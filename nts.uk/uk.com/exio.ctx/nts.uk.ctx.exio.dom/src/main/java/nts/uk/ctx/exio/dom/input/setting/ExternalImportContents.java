package nts.uk.ctx.exio.dom.input.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.setting.mapping.FixedItemMapping;
import nts.uk.ctx.exio.dom.input.setting.mapping.ImportItemMapping;

/**
 * 受入項目
 */
@Getter
@AllArgsConstructor
public class ExternalImportContents extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** CSV受入項目 */
	private List<ImportItemMapping> csvImportItem;
	
	/** 固定値項目 */
	private List<FixedItemMapping> fixedItem;
	
}
