package nts.uk.ctx.exio.dom.input.revise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.assembly.AssemblyResult;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;

/**
 * 1行分のCSV受入データを組み立てる
 */
public class AssembleCsvRecord {
	
	public static AssemblyResult assemble(Require require, ExecutionContext context, 
			List<ImportItemMapping> mapping, CsvRecord csvRecord) {
		
		val revisedResults = new ArrayList<RevisedItemResult>();
		val assemblyItems = new DataItemList();
		
		// マッピング内の項目数処理する
		for(int i = 0; i < mapping.size(); i++) {
			
			val itemNo = mapping.get(i).getImportItemNumber();
			val csvValue = csvRecord.getRawItems().get(mapping.get(i).getCsvLineNumber());
			
			// 項目の編集を取得
			val revisionist = require.getRevise(context, itemNo);
			if(revisionist.isPresent()) {
				// 編集あり
				val revisedResult =revisionist.get().revise(require, context, itemNo, csvValue);
				revisedResults.add(revisedResult);
				assemblyItems.addObject(itemNo, revisedResult.getObjectValue());
			}
			else {
				// 編集なし
				// 受入項目NOに対応する項目型に沿って型を変換する
				assemblyItems.addObject(itemNo, require.getImportableItem(context.getGroupId(), itemNo).parse(csvValue));
			}
		}
		return new AssemblyResult(assemblyItems, revisedResults);
	}
	
	public interface Require extends ReviseItem.Require{
		Optional<ReviseItem> getRevise(ExecutionContext context, int importItemNumber);
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
