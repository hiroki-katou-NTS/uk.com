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
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
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
	public Optional<RevisedDataRecord> assemble(Require require, ImportingGroupId groupId, CsvRecord csvRecord){
		
		val importData = new DataItemList();
		
		importData.addAll(assembleImportingItems(require, groupId, csvRecord));
		importData.addAll(assembleFixedItems());
		
		if(importData.isEmpty()) {
			// 受け入れられるデータがない
			return Optional.empty();
		}
		
		return Optional.of(new RevisedDataRecord(csvRecord.getRowNo(), importData));
	}

	/**
	 * 受入項目を組み立てる
	 * @param require
	 * @param context
	 * @param csvRecord
	 * @return
	 */
	private DataItemList assembleImportingItems(Require require, ImportingGroupId groupId, CsvRecord csvRecord) {
		
		return csvImportItem.stream()
				.map(m -> assembleImportingItem(require, groupId, csvRecord, m))
				.collect(collectingAndThen(toList(), DataItemList::new));
	}

	private DataItem assembleImportingItem(
			Require require, ImportingGroupId groupId, CsvRecord csvRecord, ImportItemMapping mapping) {
		
		val itemNo = mapping.getImportItemNumber();
		val csvValue = csvRecord.getItemByColumnNo(mapping.getCsvColumnNumber());
		
		// 項目の編集
		return require.getRevise(companyId, settingCode, itemNo)
				.map(r -> r.revise(require, csvValue))
				.orElseGet(() -> noRevise(require, groupId, itemNo, csvValue));
	}

	private static DataItem noRevise(Require require, ImportingGroupId groupId, int itemNo, String csvValue) {
		
		Object value = require.getImportableItem(groupId, itemNo).parse(csvValue);
		return new DataItem(itemNo, value);
	}
	
	/**
	 * 固定値項目を組み立てる
	 * @return
	 */
	private DataItemList assembleFixedItems() {
		
		return fixedItem.stream()
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
