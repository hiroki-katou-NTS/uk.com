package nts.uk.ctx.exio.dom.input.meta;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

/**
 * 受入データのメタ情報
 */
@Value
public class ImportingDataMeta {
	
	/** 会社ID */
	String companyId;
	
	/** 受入項目と正準化によって生成される項目の名称一覧 */
	List<String> itemNames;
	
	public static ImportingDataMeta create(Require require, ExecutionContext context, List<Integer> itemNos) {
		
		val itemNames = itemNos.stream()
				.map(itemNo -> require.getImportableItem(context.getGroupId(), itemNo))
				.map(item -> item.getItemName())
				.collect(toList());
		
		return new ImportingDataMeta(context.getCompanyId(), itemNames);
		
	}
	
	public ImportingDataMeta addItem(String itemName) {
		val newNames = new ArrayList<>(itemNames);
		newNames.add(itemName);
		return new ImportingDataMeta(companyId, newNames);
	}
	
	public static interface Require {
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
