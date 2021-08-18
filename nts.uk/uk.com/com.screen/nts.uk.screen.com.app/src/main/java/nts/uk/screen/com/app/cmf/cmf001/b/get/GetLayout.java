package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetLayout {
	
	@Inject
	private GetLayoutRequire require;
	
	public List<Integer> get(GetLayoutQuery query) {
		val require = this.require.create();
		
		val settingOpt = require.getSetting(AppContexts.user().companyId(), query.getSettingCode());
		if(settingOpt.isPresent()) {
			val setting = settingOpt.get();
			if(query.getImportingGroupId() == setting.getExternalImportGroupId()) {
				// 設定されている項目
				return setting.getAssembly().getMapping().getMappings().stream()
						.map(m -> m.getItemNo())
						.collect(Collectors.toList());
			}
		}
		// デフォルトで全項目
		val importableItems = require.getImportableItems(query.getImportingGroupId());
		return importableItems.stream()
				.map(i -> i.getItemNo())
				.collect(Collectors.toList());
	}
	
	public List<ExternalImportLayoutDto> getDetail(GetLayoutQuery query) {
		val require = this.require.create();
		
		val settingOpt = require.getSetting(AppContexts.user().companyId(), query.getSettingCode());
		if(settingOpt.isPresent()) {
			val setting = settingOpt.get();
			if(query.getImportingGroupId() == setting.getExternalImportGroupId()) {
				// 設定されている項目
				return setting.getAssembly().getMapping().getMappings().stream()
						.map(i -> ExternalImportLayoutDto.fromDomain(require, query.getSettingCode(), query.getImportingGroupId(), new ImportingItemMapping(i.getItemNo(), i.getCsvColumnNo(), i.getFixedValue())))
						.collect(Collectors.toList());
			}
		}
		
		val importableItems = require.getImportableItems(query.getImportingGroupId());
		return importableItems.stream()
				.map(i -> ExternalImportLayoutDto.fromDomain(require, query.getSettingCode(), query.getImportingGroupId(), new ImportingItemMapping(i.getItemNo(), Optional.empty(), Optional.empty())))
				.collect(Collectors.toList());
	}
	
	public static interface Require extends ExternalImportLayoutDto.Require {
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
	}
}
