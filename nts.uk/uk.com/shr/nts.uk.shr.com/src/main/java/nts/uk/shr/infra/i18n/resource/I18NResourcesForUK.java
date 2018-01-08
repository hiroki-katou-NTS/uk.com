package nts.uk.shr.infra.i18n.resource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import nts.arc.i18n.I18NResources;
import nts.uk.shr.com.constants.DefaultSettingKeys;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.LanguageConsts;
import nts.uk.shr.infra.i18n.loading.LanguageMasterRepository;
import nts.uk.shr.infra.i18n.resource.container.I18NResourcesRepository;

@ApplicationScoped
@Slf4j
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
		log.info("[INIT START] nts.uk.shr.infra.i18n.resource.I18NResourcesForUK");
		
		this.languageRepository.getSystemLanguages().stream()
				.map(l -> l.getLanguageId())
				.forEach(languageId -> {
					this.defaultResources.put(
							languageId, this.resourcesRepository.loadResourcesDefault(languageId));
					this.customizedResources.put(
							languageId, this.resourcesRepository.loadResourcesEachCompanies(languageId));
				});
		
		log.info("[INIT END] nts.uk.shr.infra.i18n.resource.I18NResourcesForUK");
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
	
	public Map<String, String> loadForUserByClassId(String classId) {
		
		String languageId = LanguageConsts.DEFAULT_LANGUAGE_ID;
		String companyId = DefaultSettingKeys.COMPANY_ID;
		
		if (AppContexts.user().hasLoggedIn()) {
			languageId = AppContexts.user().language().basicLanguageId();
			companyId = AppContexts.user().companyId();
		}
		
		return this.loadForUserByClassId(languageId, classId, companyId);
	}
	
	public Map<String, String> loadForUserByClassId(String languageId, String classId, String companyId) {
		
		val toMerge = this.defaultResources.createContentsMapByClassId(languageId, classId);
		val customizedMap = this.customizedResources.createContentsMapByClassId(languageId, classId, companyId);
		
		customizedMap.forEach((resourceId, contents) -> {
			toMerge.put(resourceId, contents);
		});

		return toMerge;
	}
	
	public Map<String, String> loadForUserByResourceType(I18NResourceType resourceType) {

		String languageId = LanguageConsts.DEFAULT_LANGUAGE_ID;
		String companyId = DefaultSettingKeys.COMPANY_ID;
		
		if (AppContexts.user().hasLoggedIn()) {
			languageId = AppContexts.user().language().basicLanguageId();
			companyId = AppContexts.user().companyId();
		}
		
		return this.loadForUserByResourceType(languageId, companyId, resourceType);
	}
	
	public Map<String, String> loadForUserByResourceType(String languageId, String companyId, I18NResourceType resourceType) {
		
		val toMerge = this.defaultResources.createContentsMapByResourceType(languageId, resourceType);
		val customizedMap = this.customizedResources.createContentsMapByResourceType(languageId, companyId, resourceType);
		
		customizedMap.forEach((resourceId, contents) -> {
			toMerge.put(resourceId, contents);
		});

		return toMerge;
	}
	
}
