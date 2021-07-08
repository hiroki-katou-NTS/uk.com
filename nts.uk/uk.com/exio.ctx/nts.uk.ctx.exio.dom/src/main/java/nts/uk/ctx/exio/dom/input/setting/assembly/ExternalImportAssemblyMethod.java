package nts.uk.ctx.exio.dom.input.setting.assembly;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
		importData.addAll(assembleInternal(require, context, csvRecord));
		
		// 固定値項目の組み立て
		importData.addAll(assembleFixedItem(fixedItem));
		
		if(importData.isEmpty()) {
			// 受け入れられるデータがない
			return Optional.empty();
		}
		
		return Optional.of(new RevisedDataRecord(csvRecord.getRowNo(), importData));
	}

	private DataItemList assembleInternal(Require require, ExecutionContext context, CsvRecord csvRecord) {
		
		val revisedResults = new DataItemList();
		
		// マッピング内の項目数処理する
		for (val mapping : csvImportItem) {
			
			val itemNo = mapping.getImportItemNumber();
			val csvValue = csvRecord.getItemByColumnNo(mapping.getCsvColumnNumber());
			
			// 項目の編集
			DataItem revised = require.getRevise(context.getCompanyId(), context.getExternalImportCode(), itemNo)
					.map(r -> r.revise(require, context, csvValue))
					.orElseGet(() -> noRevise(require, context, itemNo, csvValue));
			
			revisedResults.add(revised);
		}
		
		return revisedResults;
	}

	private DataItem noRevise(Require require, ExecutionContext context, final int itemNo, String csvValue) {
		
		Object value = require.getImportableItem(context.getGroupId(), itemNo).parse(csvValue);
		return new DataItem(itemNo, value);
	}
	
	// 固定値項目の組み立て
	private static DataItemList assembleFixedItem(List<FixedItemMapping> items) {
		
		return items.stream()
				.map(f -> new DataItem(f.getImportItemNumber(), f.getValue()))
				.collect(Collectors.collectingAndThen(toList(), DataItemList::new));
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
