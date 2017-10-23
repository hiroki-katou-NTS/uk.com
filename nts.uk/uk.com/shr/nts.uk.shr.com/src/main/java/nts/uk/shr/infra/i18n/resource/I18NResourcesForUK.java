package nts.uk.shr.infra.i18n.resource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import lombok.val;
import nts.arc.i18n.Locale;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.i18n.custom.ResourceType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.LanguageConsts;
import nts.uk.shr.infra.i18n.resource.container.CustomizedI18NResourceContainers;
import nts.uk.shr.infra.i18n.resource.container.I18NResourcesRepository;

@ApplicationScoped
public class I18NResourcesForUK implements IInternationalization {

	@Inject
	private I18NResourcesRepository resourcesRepository;
	
	private I18NResourcesDefault defaultResources = new I18NResourcesDefault();

	private I18NResourcesCustomized customizedResources = new I18NResourcesCustomized();
	
	private I18NResourceContentProcessor contentProcessor;
	
	@PostConstruct
	private void initialize() {
		
		Arrays.asList("ja", "en").forEach(languageId -> {
			this.loadDefault("ja");
			this.loadEachCompany(languageId);
		});
		
		this.contentProcessor = new I18NResourceContentProcessor(id -> this.getMessage(id).orElse(id));
	}
	
	private void loadDefault(String languageId) {
		this.defaultResources.put(languageId, this.resourcesRepository.loadResourcesDefault(languageId));
	}
	
	private void loadEachCompany(String languageId) {
		this.customizedResources.put(languageId, this.resourcesRepository.loadResourcesEachCompanies(languageId));
	}


	// みんなこれを使っている
	@Override
	public Optional<String> getItemName(String id, String... params) {
		return this.getMessage(id, params);
	}
	
	@Override
	public Optional<String> getMessage(String messageId, String... params) {
		return this.getMessage(messageId, Arrays.asList(params));
	}

	@Override
	public Optional<String> getMessage(String messageId, List<String> params) {
		return this.getRawContent(messageId)
				.map(content -> this.contentProcessor.process(Locale.JA, content, params));
	}

	// なぜか１箇所だけ使われている
	@Override
	public Optional<String> getRawMessage(String messageId) {
		return this.getRawContent(messageId);
	}


	@Override
	public Map<ResourceType, Map<String, String>> getResourceForProgram(String programId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getReportItems(String reportId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Map<ResourceType, Map<String, String>> getResourceOfCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<ResourceType, Map<String, String>> getSystemDefaultResource() {
		// TODO Auto-generated method stub
		return null;
	}

	
	// 要らない
	@Override
	public Map<String, String> getAllMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	// 使ってない
	@Override
	public Map<String, String> getCustomizeResource() {
		// TODO Auto-generated method stub
		return null;
	}

	private Optional<String> getRawContent(String resourceId) {
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
}
