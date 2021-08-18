package nts.uk.screen.com.app.cmf.cmf001.b.get;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
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
				return getSavedAndAddings(require, query, setting);
			}
		}
		
		return getAllImportables(require, query);
	}

	private List<ExternalImportLayoutDto> getSavedAndAddings(
			GetLayout.Require require,
			GetLayoutQuery query, 
			ExternalImportSetting setting) {
		
		// 設定されている項目
		val savedLayouts = toLayouts(require, query, setting.getAssembly().getMapping().getMappings());
		
		val savedItemNos = savedLayouts.stream().map(l -> l.getItemNo()).collect(Collectors.toSet());
		val addingItemNos = query.getItemNoList().stream()
				.filter(n -> !savedItemNos.contains(n))
				.collect(Collectors.toSet());
		
		// 画面上で追加された項目
		val addings = getAllImportables(require, query).stream()
				.filter(l -> addingItemNos.contains(l.getItemNo()))
				.collect(toList());
		
		val results = new ArrayList<ExternalImportLayoutDto>();
		results.addAll(savedLayouts);
		results.addAll(addings);
		
		return results;
	}

	private List<ExternalImportLayoutDto> toLayouts(GetLayout.Require require, GetLayoutQuery query, List<ImportingItemMapping> mappings) {
		
		return mappings.stream()
				.map(i -> ExternalImportLayoutDto.fromDomain(
						require,
						query.getSettingCode(),
						query.getImportingGroupId(),
						new ImportingItemMapping(i.getItemNo(), i.getCsvColumnNo(), i.getFixedValue())))
				.collect(Collectors.toList());
	}

	private static List<ExternalImportLayoutDto> getAllImportables(GetLayout.Require require, GetLayoutQuery query) {
		
		val importableItems = require.getImportableItems(query.getImportingGroupId());
		
		return importableItems.stream()
				.map(i -> ExternalImportLayoutDto.fromDomain(
						require,
						query.getSettingCode(),
						query.getImportingGroupId(),
						ImportingItemMapping.noSetting(i.getItemNo())))
				.collect(Collectors.toList());
	}
	
	public static interface Require extends ExternalImportLayoutDto.Require {
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
	}
}
