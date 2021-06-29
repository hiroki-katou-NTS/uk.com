package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.revise.AssembleCsvRecord;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FixedItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;

/**
 * 受入データの組み立て方法
 */
@Getter
@AllArgsConstructor
public class ExternalImportAssemblyMethod extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** CSV受入項目 */
	private List<ImportItemMapping> csvImportItem;
	
	/** 固定値項目 */
	private List<FixedItemMapping> fixedItem;
	
	
	public Optional<RevisedDataRecord> assembleExternalImportData(Require require, ExecutionContext context, CsvRecord csvRecord){
		
		val importData = new RevisedDataRecord();
		
		// CSVの取込内容を組み立てる
		val csvAssemblyResult = AssembleCsvRecord.assemble(require, context, csvImportItem, csvRecord);
		if(csvAssemblyResult.isIncorrectData()) {
			// 不正なデータが1件でもあれば処理中の行は取り込まない
			return failedAssemble();
		}
		importData.addItemList(csvAssemblyResult.getAssemblyItem());
		
		// 固定値項目の組み立て
		importData.addItemList(this.assembleFixedItem(fixedItem));
		
		if(importData.getItems().isEmpty()) {
			// 受け入れられるデータがない
			return Optional.empty();
		}
		return Optional.of(importData);
	}
	
	// 固定値項目の組み立て
	private DataItemList assembleFixedItem(List<FixedItemMapping> items) {
		val importData = new DataItemList();
		
		for(int i = 0; i < items.size(); i++) {
			importData.add(DataItem.of(items.get(i).getImportItemNumber(), items.get(i).getValue()));
		}
		return importData;
	}
	
	// 組み立て失敗
	private Optional<RevisedDataRecord> failedAssemble(){
		return Optional.empty();
	}
	
	public interface Require extends AssembleCsvRecord.Require {
	}
}
