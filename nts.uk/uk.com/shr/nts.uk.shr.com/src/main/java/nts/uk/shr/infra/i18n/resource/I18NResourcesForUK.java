package nts.uk.shr.infra.i18n.resource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.val;
import nts.arc.i18n.I18NResources;
import nts.uk.shr.com.constants.DefaultSettingKeys;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.LanguageConsts;
import nts.uk.shr.infra.i18n.loading.LanguageMasterRepository;
import nts.uk.shr.infra.i18n.resource.container.I18NResourcesRepository;

@ApplicationScoped
public class I18NResourcesForUK implements I18NResources {

	@Inject
	private I18NResourcesRepository resourcesRepository;
	
	@Inject
	private LanguageMasterRepository languageRepository;
	
	private I18NResourcesDefault defaultResources = new I18NResourcesDefault();

	private I18NResourcesCustomized customizedResources = new I18NResourcesCustomized();
	
	private I18NResourceContentProcessor contentProcessor = new I18NResourceContentProcessor(
			id -> this.localize(id).orElse(id));
	
	@PostConstruct
	private void initialize() {
		
		this.languageRepository.getSystemLanguages().stream()
				.map(l -> l.getLanguageId())
				.forEach(languageId -> {
					this.defaultResources.put(
							languageId, this.resourcesRepository.loadResourcesDefault(languageId));
					this.customizedResources.put(
							languageId, this.resourcesRepository.loadResourcesEachCompanies(languageId));
				});
	}

	@Override
	public Optional<String> getRawContent(String resourceId) {
		String languageId = LanguageConsts.DEFAULT_LANGUAGE_ID;

		if (AppContexts.user().hasLoggedIn()) {
			languageId = AppContexts.user().language().basicLanguageId();
			String companyId = AppContexts.user().companyId();
			
			val customizedOptional = this.customizedResources.getContent(companyId, languageId, resourceId);
			if (customizedOptional.isPresent()) {
				return customizedOptional;
			}
		}
		
		return this.defaultResources.getContent(languageId, resourceId);
	}

	@Override
	public Optional<String> localize(String resourceId, List<String> params) {
		return this.getRawContent(resourceId)
				.map(content -> this.contentProcessor.process(LanguageConsts.DEFAULT_LANGUAGE_ID, content, params));
	}
	
	public Map<String, String> loadAllForUser() {
		
		String languageId = LanguageConsts.DEFAULT_LANGUAGE_ID;
		String companyId = DefaultSettingKeys.COMPANY_ID;
		
		if (AppContexts.user().hasLoggedIn()) {
			languageId = AppContexts.user().language().basicLanguageId();
			companyId = AppContexts.user().companyId();
		}
		
		return this.loadAllForUser(languageId, companyId);
	}
	
	public Map<String, String> loadAllForUser(String languageId, String companyId) {
		
		val toMerge = this.defaultResources.createContentsMap(languageId);
		val customizedMap = this.customizedResources.createContentsMap(languageId, companyId);
		
		customizedMap.forEach((resourceId, contents) -> {
			toMerge.put(resourceId, contents);
		});

		return toMerge;
	}
}
