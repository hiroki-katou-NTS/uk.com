package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;

/**
 * 項目マッピング
 */
@RequiredArgsConstructor
@Getter
public class ImportingItemMapping {

	/** 項目NO */
	private final int itemNo;
	
	/** CSV列番号 */
	private Optional<Integer> csvColumnNo;
	
	/** 固定値 */
	private Optional<StringifiedValue> fixedValue;

	public ImportingItemMapping(int itemNo, Optional<Integer> csvColumnNo, Optional<StringifiedValue> fixedValue) {
		
		if (csvColumnNo.isPresent() && fixedValue.isPresent()) {
			throw new RuntimeException("両方同時には設定できない");
		}
		
		this.itemNo = itemNo;
		this.csvColumnNo = csvColumnNo;
		this.fixedValue = fixedValue;
	}
	
	/**
	 * 設定済みならtrue
	 * @return
	 */
	public boolean isConfigured() {
		return csvColumnNo.isPresent() || fixedValue.isPresent();
	}
	
	public DataItem assemble(RequireAssemble require, ExecutionContext context, CsvRecord csvRecord) {
		
		if (!isConfigured()) {
			throw new RuntimeException("未設定");
		}
		
		// 固定値
		if (fixedValue.isPresent()) {
			val importableItem = require.getImportableItem(context.getGroupId(), itemNo);
			Object value = fixedValue.get().asTypeOf(importableItem.getItemType());
			return new DataItem(itemNo, value);
		}
		
		// CSV項目
		else {
			val csvItem = readCsv(csvRecord);
			
			// 編集
			return require.getReviseItem(context.getCompanyId(), context.getExternalImportCode(), itemNo)
					.map(r -> r.revise(csvItem.getCsvValue()))
					.orElseGet(() -> noRevise(require, context, csvItem));
		}
	}
	
	private ImportingCsvItem readCsv(CsvRecord record) {
		
		String value = record.getItemByColumnNo(csvColumnNo.get())
				.orElseThrow(() -> new RuntimeException("列が存在しない：" + csvColumnNo));
		
		return new ImportingCsvItem(itemNo, value);
	}

	private static DataItem noRevise(RequireAssemble require, ExecutionContext context, ImportingCsvItem csvItem) {
		
		Object value = require.getImportableItem(context.getGroupId(), csvItem.getItemNo())
				.parse(csvItem.getCsvValue());
		
		return new DataItem(csvItem.getItemNo(), value);
	}

	public static interface RequireAssemble {
		
		Optional<ReviseItem> getReviseItem(String companyId, ExternalImportCode settingCode, int itemNo);
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
