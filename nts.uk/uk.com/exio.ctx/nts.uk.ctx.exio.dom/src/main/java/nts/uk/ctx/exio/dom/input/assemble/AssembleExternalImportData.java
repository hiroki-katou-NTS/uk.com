package nts.uk.ctx.exio.dom.input.assemble;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.revise.ReviseCsvRecord;
import nts.uk.ctx.exio.dom.input.revise.RevisedItemResult;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportContents;
import nts.uk.ctx.exio.dom.input.setting.mapping.FixedItemMapping;

/**
 * 1行分の受入データを組み立てる
 */
public class AssembleExternalImportData {
	
	public Optional<ExternalImportData> assemble(Require require, ExecutionContext context, CsvRecord csvRecord) {
		
		val importData = new ExternalImportData();
		
		val contents = require.getContents(context);
		
		// CSV受入項目があればその項目を組み立て
		if(contents.getCsvImportItem().size() > 0) {
			// CSVの取込内容を編集する
			val reviseResult = ReviseCsvRecord.revise(require, context, csvRecord, contents.getCsvImportItem());
			if(isError(reviseResult)) {
				// エラーが1件でもあれば処理中の行は取り込まない
				return Optional.empty();
			}
			importData.addItemList(this.assembleCsvImportItem(reviseResult));
		}
		// 固定値項目があればその項目を組み立て
		if(contents.getFixedItem().size() > 0) {
			importData.addItemList(this.assembleFixedItem(contents.getFixedItem()));
		}
		if(importData.getOneRecordData().isEmpty()) {
			// 受け入れられるデータがない
			return Optional.empty();
		}
		return Optional.of(importData);
	}
	
	// CSV受入値項目の組み立て
	private DataItemList assembleCsvImportItem(List<RevisedItemResult> items) {
		val importData = new DataItemList();
		
		for(int i = 1; i <= items.size(); i++) {
			val itemNo = items.get(i).getItemNo();
			val value = items.get(i).getObjectValue().get();
			importData.add(DataItem.of(itemNo, value));
		}
		return importData;
	}
	
	// 固定値項目の組み立て
	private DataItemList assembleFixedItem(List<FixedItemMapping> items) {
		val importDatas = new DataItemList();
		
		for(int i = 0; i < items.size(); i++) {
			importDatas.add(DataItem.of(items.get(i).getImportItemNumber(), items.get(i).getValue()));
		}
		return importDatas;
	}
	
	// エラーがあるか
	private boolean isError(List<RevisedItemResult> items) {
		for(int i = 0; i < items.size(); i++) {
			if(!items.get(i).isSuccess()) {
				// エラーが1件でもあればtrue
				return true;
			}
		}
		// エラーが1件もない場合
		return false;
	}
	
	public interface Require extends ReviseCsvRecord.Require {
		ExternalImportContents getContents(ExecutionContext context);
	}
}
