package nts.uk.ctx.exio.app.input.find.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.shr.com.context.AppContexts;

@Value
public class ExternalImportSettingDto {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
	
	/** 受入グループID */
	private int group;
	
	/** 受入モード */
	private int mode;
	
	/** CSVの項目名取得行 */
	private int itemNameRow;
	
	/** CSVの取込開始行 */
	private int importStartRow;
	
	/** レイアウト項目リスト */
	private List<Integer> itemNoList;
	
	public static ExternalImportSettingDto fromDomain(Require require, ExternalImportSetting domain) {
		
		return new ExternalImportSettingDto(
				domain.getCompanyId(), 
				domain.getCode().toString(), 
				domain.getName().toString(), 
				domain.getExternalImportGroupId().value, 
				domain.getImportingMode().value, 
				domain.getAssembly().getCsvFileInfo().getItemNameRowNumber().hashCode(), 
				domain.getAssembly().getCsvFileInfo().getImportStartRowNumber().hashCode(), 
				domain.getAssembly().getMapping().getMappings().stream()
				.map(m -> m.getItemNo())
				.collect(Collectors.toList()));
	}
	
	public ExternalImportSetting toDomain(Require require) {
		return new ExternalImportSetting(
				companyId, 
				new ExternalImportCode(code), 
				new ExternalImportName(name), 
				ImportingGroupId.valueOf(group), 
				ImportingMode.valueOf(mode), 
				new ExternalImportAssemblyMethod(
						new ExternalImportCsvFileInfo(
								new ExternalImportRowNumber(itemNameRow), 
								new ExternalImportRowNumber(importStartRow)), 
						new ImportingMapping(createMappings(require))));
	}
	
	private List<ImportingItemMapping> createMappings(Require require){
		List<ImportingItemMapping> result = new ArrayList<>();
		
		for(int itemNo: itemNoList) {
			val optRegisteredSetting = require.getSetting(AppContexts.user().companyId(), new ExternalImportCode(code));
			if(optRegisteredSetting.isPresent()) {
				List<ImportingItemMapping> mappings = optRegisteredSetting.get().getAssembly().getMapping().getMappings();
				val configuredItem = mappings.stream().filter(m -> m.getItemNo() == itemNo).collect(Collectors.toList());
				if(configuredItem.size() > 0) {
					result.addAll(configuredItem);
				}
			}
			result.add(new ImportingItemMapping(itemNo, Optional.empty(), Optional.empty()));
		}
		return result;
	}
	
	public static interface Require {
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
	}
}
