package nts.uk.ctx.exio.app.input.find.setting.assembly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;

@Value
public class ExternalImportLayoutDto {
	
	/** 項目NO */
	private int itemNo;
	
	/** 項目名 */
	private String name;
	
	/** 項目型 */
	private String type;
	
	/** 受入元 */
	private String source;
	
	/** 詳細設定の有無 */
	private boolean alreadyDetail;
	
	public static ExternalImportLayoutDto fromDomain(Require require, ImportingGroupId groupId, ImportingItemMapping mapping) {
		return new ExternalImportLayoutDto(
				mapping.getItemNo(), 
				getItemName(require, groupId, mapping),
				getItemType(require, groupId, mapping),
				checkImportSource(mapping),
				false);
	}
	
	private static String getItemName(Require require, ImportingGroupId groupId, ImportingItemMapping mapping) {
		val importableItems = require.getImportableItems(groupId);
		return importableItems.stream()
				.filter(i -> i.getItemNo() == mapping.getItemNo()).collect(Collectors.toList()).get(0).getItemName();
	}
	
	private static String getItemType(Require require, ImportingGroupId groupId, ImportingItemMapping mapping) {
		val importableItems = require.getImportableItems(groupId);
		return importableItems.stream()
				.filter(i -> i.getItemNo() == mapping.getItemNo()).collect(Collectors.toList()).get(0).getItemType().name();
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
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
	}
}
