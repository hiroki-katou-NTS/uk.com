package nts.uk.shr.infra.i18n.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.uk.shr.infra.i18n.resource.container.CustomizedI18NResourceContainers;

public class I18NResourcesCustomized {

	/** LanguageId -> containers */
	private Map<String, CustomizedI18NResourceContainers<?>> resources = new HashMap<>();
	
	public Optional<String> getContent(String companyId, String language, String resourceId) {
		
		val containers = this.resources.getOrDefault(
				language, CustomizedI18NResourceContainers.empty());
		
		val container = containers.containerOf(companyId);
		
		return container.getContentOptional(resourceId);
	}
	
	public void put(String languageId, CustomizedI18NResourceContainers<?> containers) {
		this.resources.put(languageId, containers);
	}
}
