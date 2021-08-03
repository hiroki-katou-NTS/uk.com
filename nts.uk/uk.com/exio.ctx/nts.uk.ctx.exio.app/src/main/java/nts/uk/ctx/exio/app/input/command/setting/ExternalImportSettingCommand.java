package nts.uk.ctx.exio.app.input.command.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;

@Getter
public class ExternalImportSettingCommand {
	
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
	
	/** レイアウト */
	private List<ExternalImportLayoutCommand> layout;
	
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
		val registeredMappings = require.getMappings(ImportingGroupId.valueOf(group));
		if(registeredMappings.size() > 0) {
			return registeredMappings;
		}
		return layout.stream()
				.map(l -> new ImportingItemMapping(l.itemNo, null, null))
				.collect(Collectors.toList());
	}
	
	@Value
	private static class ExternalImportLayoutCommand {
		
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
	}
	
	public static interface Require {
		List<ImportingItemMapping> getMappings(ImportingGroupId groupId);
	}
}
