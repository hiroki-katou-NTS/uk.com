package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.shr.com.context.AppContexts;

@Value
public class ExternalImportLayoutDto {
	
	/** 項目NO */
	private int itemNo;
	
	/** 項目名 */
	private String name;
	
	/** 必須項目 */
	private boolean required;
	
	/** 項目型 */
	private String type;
	
	/** 受入元 */
	private String source;
	
	/** 詳細設定の有無 */
	private boolean alreadyDetail;
	
	public static ExternalImportLayoutDto fromDomain(Require require, ExternalImportCode settingCode, ImportingGroupId groupId, ImportingItemMapping domain) {
		return new ExternalImportLayoutDto(
				domain.getItemNo(), 
				getItemName(require, groupId, domain),
				checkRequired(require, groupId, domain), 
				getItemType(require, groupId, domain),
				checkImportSource(domain),
				checkAlreadyDetail(require, settingCode, domain));
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
	
	private static boolean checkRequired(Require require, ImportingGroupId groupId, ImportingItemMapping mapping) {
		val importableItems = require.getImportableItems(groupId);
		return importableItems.stream()
				.filter(i -> i.getItemNo() == mapping.getItemNo()).collect(Collectors.toList()).get(0).isRequired();
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
	
	private static boolean checkAlreadyDetail(Require require, ExternalImportCode settingCode, ImportingItemMapping mapping) {
		return require.getRevise(AppContexts.user().companyId(), settingCode, mapping.getItemNo()).isPresent();
	}
	
	public static interface Require {
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
		Optional<ReviseItem> getRevise(String companyId, ExternalImportCode settingCode, int itemNo);
	}
}