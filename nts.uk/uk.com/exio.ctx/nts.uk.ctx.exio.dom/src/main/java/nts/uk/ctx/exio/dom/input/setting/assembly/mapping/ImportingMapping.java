package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;

/**
 * 受入マッピング
 */
@Value
public class ImportingMapping {
	
	/** CSV受入項目 */
	private List<ImportItemMapping> csvImportItems;
	
	/** 固定値項目 */
	private List<FixedItemMapping> fixedItems;
	
	public DataItemList assemble(RequireAssemble require, ExecutionContext context, CsvRecord csvRecord) {
		
		val importData = new DataItemList();
		importData.addAll(assembleImportingItems(require, context, csvRecord));
		importData.addAll(assembleFixedItems());
		
		return importData;
	}
	
	public static interface RequireAssemble {

		Optional<ReviseItem> getReviseItem(String companyId, ExternalImportCode settingCode, int itemNo);
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}

	/**
	 * 受入項目を組み立てる
	 * @param require
	 * @param context
	 * @param csvRecord
	 * @return
	 */
	private DataItemList assembleImportingItems(RequireAssemble require, ExecutionContext context, CsvRecord csvRecord) {
		
		return csvImportItems.stream()
				.map(m -> assembleImportingItem(require, context, m.read(csvRecord)))
				.collect(collectingAndThen(toList(), DataItemList::new));
	}

	private DataItem assembleImportingItem(RequireAssemble require, ExecutionContext context, ImportingCsvItem csvItem) {
		
		val settingCode = new ExternalImportCode(context.getSettingCode());
		
		return require.getReviseItem(context.getCompanyId(), settingCode, csvItem.getItemNo())
				.map(r -> r.revise(csvItem.getCsvValue()))
				.orElseGet(() -> noRevise(require, context, csvItem));
	}

	private static DataItem noRevise(RequireAssemble require, ExecutionContext context, ImportingCsvItem csvItem) {
		
		Object value = require.getImportableItem(context.getGroupId(), csvItem.getItemNo())
				.parse(csvItem.getCsvValue());
		
		return new DataItem(csvItem.getItemNo(), value);
	}
	
	/**
	 * 固定値項目を組み立てる
	 * @return
	 */
	private DataItemList assembleFixedItems() {
		
		return fixedItems.stream()
				.map(f -> new DataItem(f.getImportItemNumber(), f.getValue()))
				.collect(Collectors.collectingAndThen(toList(), DataItemList::new));
	}
	
	public List<Integer> getAllItemNo() {
		
		return Stream.concat(
				csvImportItems.stream().map(i -> i.getItemNo()),
				fixedItems.stream().map(i -> i.getImportItemNumber()))
				.collect(toList());
	}
}
