package nts.uk.ctx.exio.dom.input.setting.assembly;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.revise.RevisedItemResult;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FixedItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;

/**
 * 受入データの組み立て方法
 */
@Getter
@AllArgsConstructor
public class ExternalImportAssemblyMethod {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** CSV受入項目 */
	private List<ImportItemMapping> csvImportItem;
	
	/** 固定値項目 */
	private List<FixedItemMapping> fixedItem;
	
	/** CSVファイル情報 */
	private ExternalImportCsvFileInfo csvFileInfo;
	
	/**
	 * 組み立てる
	 * @param require
	 * @param context
	 * @param csvRecord
	 * @return
	 */
	public Optional<RevisedDataRecord> assemble(Require require, ExecutionContext context, CsvRecord csvRecord){
		
		val importData = new DataItemList();
		
		// CSVの取込内容を組み立てる
		val csvAssemblyResult = assembleInternal(require, context, csvRecord);
		if(csvAssemblyResult.isIncorrectData()) {
			// 不正なデータが1件でもあれば処理中の行は取り込まない
			return Optional.empty();
		}
		importData.addItemList(csvAssemblyResult.getAssemblyItem());
		
		// 固定値項目の組み立て
		importData.addItemList(assembleFixedItem(fixedItem));
		
		if(importData.isEmpty()) {
			// 受け入れられるデータがない
			return Optional.empty();
		}
		
		return Optional.of(new RevisedDataRecord(csvRecord.getRowNo(), importData));
	}

	private AssemblyResult assembleInternal(Require require, ExecutionContext context, CsvRecord csvRecord) {
		
		val revisedResults = new ArrayList<RevisedItemResult>();
		val assemblyItems = new DataItemList();
		
		// マッピング内の項目数処理する
		for(int i = 0; i < csvImportItem.size(); i++) {
			
			val itemNo = csvImportItem.get(i).getImportItemNumber();
			val csvValue = csvRecord.getItemByColumnNo(csvImportItem.get(i).getCsvColumnNumber());
			
			// 項目の編集を取得
			val revisionist = require.getRevise(context.getCompanyId(), context.getExternalImportCode(), itemNo);
			if(revisionist.isPresent()) {
				// 編集あり
				val revisedResult =revisionist.get().revise(require, context, csvValue);
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
	
	// 固定値項目の組み立て
	private static DataItemList assembleFixedItem(List<FixedItemMapping> items) {
		
		val importData = new DataItemList();
		
		for (int i = 0; i < items.size(); i++) {
			importData.add(DataItem.of(items.get(i).getImportItemNumber(), items.get(i).getValue()));
		}
		
		return importData;
	}
	
	public List<Integer> getAllItemNo() {
		return Stream.concat(
				csvImportItem.stream().map(i -> i.getImportItemNumber()),
				fixedItem.stream().map(i -> i.getImportItemNumber()))
				.collect(toList());
	}
	
	public interface Require extends ReviseItem.Require {

		Optional<ReviseItem> getRevise(String companyId, ExternalImportCode importCode, int importItemNumber);
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
