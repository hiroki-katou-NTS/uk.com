package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportErrorsRequire;

/**
 * 受入マッピング
 */
@Value
public class ImportingMapping {
	
	/** マッピング一覧 */
	private List<ImportingItemMapping> mappings;
	
	public DataItemList assemble(RequireAssemble require, ExecutionContext context, CsvRecord csvRecord) {
		
		val results = new DataItemList();
		
		for (val mapping : mappings) {
			
			mapping.assemble(require, context, csvRecord)
				.ifRight(r -> results.add(r))
				.ifLeft(e -> require.add(context, ExternalImportError.of(csvRecord.getRowNo(), e)));
		}
		
		return results;
	}
	
	public static interface RequireAssemble extends
			ImportingItemMapping.RequireAssemble,
			ExternalImportErrorsRequire {
	}
	
	public List<Integer> getAllItemNo() {
		
		return mappings.stream()
				.map(i -> i.getItemNo())
				.collect(toList());
	}
	
	/**
	 * 項目を取得する
	 * @param itemNo
	 * @return
	 */
	public Optional<ImportingItemMapping> getByItemNo(int itemNo) {
		
		return mappings.stream()
				.filter(m -> m.getItemNo() == itemNo)
				.findFirst();
	}
	
	/**
	 * 指定した項目を未設定に変更する
	 */
	public void setNoSetting(int itemNo) {
		
		getByItemNo(itemNo).get().setNoSetting();
		resetCsvColumnNoByOrder();
	}
	
	/**
	 * 指定した項目をCSVマッピングに変更する
	 */
	public void setCsvMapping(int itemNo) {
		
		// 一旦ダミーの値を入れてCSVマッピングに切り替えた状態で順番リセット
		getByItemNo(itemNo).get().setCsvColumnNo(-1);
		resetCsvColumnNoByOrder();
	}
	
	/**
	 * 指定した項目を固定値に変更する
	 */
	public void setFixedValue(int itemNo, StringifiedValue fixedValue) {
		
		getByItemNo(itemNo).get().setFixedValue(fixedValue);
		resetCsvColumnNoByOrder();
	}

	private void resetCsvColumnNoByOrder() {
		
		int columnNo = 1;
		
		for (val mapping : mappings) {
			if (mapping.isCsvMapping()) {
				mapping.setCsvColumnNo(columnNo);
				columnNo++;
			}
		}
	}
}
