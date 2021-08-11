package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalImportSettingFinder {
	
	@Inject
	private ExternalImportSettingRequire require;
	
	public List<ExternalImportSettingListItemDto> findAll() {
		val require = this.require.create();
		val settings = require.getSettings(AppContexts.user().companyId());
		return ExternalImportSettingListItemDto.fromDomain(settings);
	}
	
	public ExternalImportSettingDto find(String settingCode) {
		val require = this.require.create();
		val settingOpt = require.getSetting(AppContexts.user().companyId(), new ExternalImportCode(settingCode));
		return ExternalImportSettingDto.fromDomain(require, settingOpt.get());
	}
	
	public List<ExternalImportLayoutDto> findLayout(FindExternalImportSettingLayoutQuery query) {
		val require = this.require.create();
		val settingOpt = require.getSetting(AppContexts.user().companyId(), new ExternalImportCode(query.getSettingCode()));
		if(!settingOpt.isPresent()) {
			return new ArrayList<>();
		}
		val mapping = settingOpt.get().getAssembly().getMapping().getMappings();
		return mapping.stream().map(m -> ExternalImportLayoutDto.fromDomain(require, settingOpt.get().getExternalImportGroupId(), m)).collect(Collectors.toList());
	}
	
	public static interface Require extends ExternalImportSettingDto.Require, ExternalImportLayoutDto.Require {
		List<ExternalImportSetting> getSettings(String companyId);
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
	}
}
