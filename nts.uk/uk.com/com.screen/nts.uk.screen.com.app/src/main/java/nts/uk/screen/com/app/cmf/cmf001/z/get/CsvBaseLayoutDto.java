package nts.uk.screen.com.app.cmf.cmf001.z.get;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;

@Value
public class CsvBaseLayoutDto {

	/** 項目NO */
	private int itemNo;

	/** 項目名 */
	private String name;

	/** 必須項目 */
	private boolean required;

	/** 受入元 */
	private String source;

	private Optional<Integer> selectedCsvItemNo;
	
	private String csvData;

	public static CsvBaseLayoutDto fromDomain(
			List<ImportableItem> importableItems,
			ExternalImportCode settingCode,
			ImportingDomainId domainId,
			ImportingItemMapping domain,
			Optional<Integer> selectedCsvItemNo,
			String csvData) {

		return new CsvBaseLayoutDto(
				domain.getItemNo(),
				getItemName(importableItems, domainId, domain),
				checkRequired(importableItems, domainId, domain),
				checkImportSource(domain),
				selectedCsvItemNo,
				csvData);
	}

	private static String getItemName(List<ImportableItem> importableItems, ImportingDomainId domainId, ImportingItemMapping mapping) {
		return importableItems.stream()
				.filter(i -> i.getItemNo() == mapping.getItemNo()).collect(Collectors.toList()).get(0).getItemName();
	}

	private static boolean checkRequired(List<ImportableItem> importableItems, ImportingDomainId domainId, ImportingItemMapping mapping) {
		boolean required = importableItems.stream()
								.filter(i -> i.getItemNo() == mapping.getItemNo())
								.collect(Collectors.toList()).get(0).isRequired();

		boolean primary = importableItems.stream()
								.filter(i -> i.getItemNo() == mapping.getItemNo())
								.collect(Collectors.toList()).get(0).isPrimaryKey();


		return required || primary;
	}

	private static String checkImportSource(ImportingItemMapping mapping) {
		val optCsvColumnNo = mapping.getCsvColumnNo();
		val optFixedValue = mapping.getFixedValue();
		if(optCsvColumnNo.isPresent()){
			return "CSV";
		}
		else if(optFixedValue.isPresent()){
			return "固定値";
		}
		else {
			return "未設定";
		}
	}


	public static interface Require {
		List<ImportableItem> getImportableItems(ImportingDomainId domainId);
	}
}