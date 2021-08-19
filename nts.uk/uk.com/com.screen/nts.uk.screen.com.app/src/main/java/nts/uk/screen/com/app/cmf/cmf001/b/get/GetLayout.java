package nts.uk.screen.com.app.cmf.cmf001.b.get;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
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
			if(query.getImportingDomainId() == setting.getExternalImportDomainId()) {
				// 設定されている項目
				return setting.getAssembly().getMapping().getMappings().stream()
						.map(m -> m.getItemNo())
						.collect(Collectors.toList());
			}
		}
		// デフォルトで全項目
		val importableItems = require.getImportableItems(query.getImportingDomainId());
		return importableItems.stream()
				.map(i -> i.getItemNo())
				.collect(Collectors.toList());
	}
	
	public List<ExternalImportLayoutDto> getDetail(GetLayoutQuery query) {
		val require = this.require.create();
		
		val settingOpt = require.getSetting(AppContexts.user().companyId(), query.getSettingCode());
		if(settingOpt.isPresent()) {
			// 設定あり（更新モード）
			val setting = settingOpt.get();
			if(query.getImportingDomainId() == setting.getExternalImportDomainId()) {
				// グループIDがマスタと一致
				if(query.isAllItem()) {
					// 登録済みのレイアウトを取得
					return getSaved(require, query, setting);
				}else {
					// 指定した項目のレイアウトを取得
					return getSpecified(require, query, setting);
				}
			}else {
				// グループIDがマスタと不一致
				if(query.isAllItem()) {
					// 受入可能項目をすべて取得
					return getAllImportables(require, query);
				}else {
					// 指定した項目のレイアウトを取得
					return getSpecified(require, query, setting);
				}
			}
		}
		// 設定なし（新規モード）
		return getAllImportables(require, query);
	}
	
	// 登録済みのレイアウトを取得する
	private List<ExternalImportLayoutDto> getSaved(
			GetLayout.Require require,
			GetLayoutQuery query, 
			ExternalImportSetting setting) {
		return toLayouts(require, query, setting.getAssembly().getMapping().getMappings());
	}
	
	// 指定した項目のレイアウトを取得する
	private List<ExternalImportLayoutDto> getSpecified(
			GetLayout.Require require,
			GetLayoutQuery query, 
			ExternalImportSetting setting) {
		
		
		val savedMapping = setting.getAssembly().getMapping().getMappings().stream()
				.filter(m -> query.getItemNoList().contains(m.getItemNo()))
				.collect(toList());
		
		// 指定した項目のうち登録済みの項目
		val savedLayouts = toLayouts(require, query, savedMapping);
		
		val savedItemNos = savedLayouts.stream().map(l -> l.getItemNo()).collect(Collectors.toSet());
		val unservedItemNos = query.getItemNoList().stream()
				.filter(n -> !savedItemNos.contains(n))
				.collect(Collectors.toSet());
		
		// 指定した項目のうち未登録の項目		
		val unservedLayouts = getAllImportables(require, query).stream()
				.filter(l -> unservedItemNos.contains(l.getItemNo()))
				.collect(toList());
		
		val results = new ArrayList<ExternalImportLayoutDto>();
		results.addAll(savedLayouts);
		results.addAll(unservedLayouts);
		
		return results;
	}

	private List<ExternalImportLayoutDto> toLayouts(GetLayout.Require require, GetLayoutQuery query, List<ImportingItemMapping> mappings) {
		
		return mappings.stream()
				.map(i -> ExternalImportLayoutDto.fromDomain(
						require,
						query.getSettingCode(),
						query.getImportingDomainId(),
						new ImportingItemMapping(i.getItemNo(), i.getCsvColumnNo(), i.getFixedValue())))
				.collect(Collectors.toList());
	}

	private static List<ExternalImportLayoutDto> getAllImportables(GetLayout.Require require, GetLayoutQuery query) {
		
		val importableItems = require.getImportableItems(query.getImportingDomainId());
		
		return importableItems.stream()
				.map(i -> ExternalImportLayoutDto.fromDomain(
						require,
						query.getSettingCode(),
						query.getImportingDomainId(),
						ImportingItemMapping.noSetting(i.getItemNo())))
				.collect(Collectors.toList());
	}
	
	public static interface Require extends ExternalImportLayoutDto.Require {
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
		List<ImportableItem> getImportableItems(ImportingDomainId domainId);
	}
}
