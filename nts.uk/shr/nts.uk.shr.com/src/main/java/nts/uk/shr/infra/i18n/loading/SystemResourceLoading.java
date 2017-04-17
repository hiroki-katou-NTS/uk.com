package nts.uk.shr.infra.i18n.loading;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nts.arc.i18n.custom.ISystemResourceBundle;
import nts.arc.layer.infra.data.EntityManagerLoader;
import nts.uk.shr.infra.i18n.ResourceType;
import nts.uk.shr.infra.i18n.SystemProperties;
import nts.uk.shr.infra.i18n.entity.SystemResource;

@Startup
@Singleton
public class SystemResourceLoading implements ISystemResourceBundle {
	private static String SELECT_ALL = "Select e from SystemResource e";

	/**
	 * if key is program id then values contains only codename see:
	 * SystemProperties, if key is system_id then values are system default code
	 * name, and message, and enum
	 * Map<Locale,Map<Type,Map<ProgramID,Map<id,content>>>>
	 */
	private final Map<Locale, Map<ResourceType, Map<String, Map<String, String>>>> systemResourceGroupByProgram;

	@Inject
	public SystemResourceLoading(EntityManagerLoader entityManagerLoader) {
		Map<Locale, Map<ResourceType, Map<String, Map<String, String>>>> tempGroupByProgram = new HashMap<>();

		EntityManager em = entityManagerLoader.getEntityManager();
		List<SystemResource> resource = em.createQuery(SELECT_ALL, SystemResource.class).getResultList();

		// group by language
		Map<String, List<SystemResource>> resourceGroupingByLanguage = resource.stream()
				.collect(Collectors.groupingBy(SystemResource::getLanguageCode, Collectors.toList()));

		resourceGroupingByLanguage.forEach((k, v) -> {

			// Map<Locale,Map<Type,Map<ProgramID,Map<id,content>>>>
			Map<ResourceType, Map<String, Map<String, String>>> groupByProgram = v.stream().collect(
					Collectors.groupingBy(SystemResource::getType, Collectors.groupingBy(SystemResource::getProgramId,
							Collectors.toMap(SystemResource::getCode, SystemResource::getContent))));

			tempGroupByProgram.put(Locale.forLanguageTag(k), groupByProgram);

		});

		systemResourceGroupByProgram = tempGroupByProgram;

	}

	@Override
	public Map<String, String> getCodeNameOfProgram(Locale locale, String programId) {

		Map<ResourceType, Map<String, Map<String, String>>> resource = systemResourceGroupByProgram.getOrDefault(locale,
				new HashMap<ResourceType, Map<String, Map<String, String>>>());
		// get default language resource if specify language not found
		if (resource == null) {
			resource = systemResourceGroupByProgram.get(SystemProperties.DEFAULT_LANGUAGE);
		}

		Map<String, String> result = resource
				.getOrDefault(ResourceType.CODE_NAME, new HashMap<String, Map<String, String>>()).get(programId);

		return result == null ? new HashMap<String, String>() : Collections.unmodifiableMap(result);
	}

	@Override
	public Map<String, String> getCodeNameResource(Locale locale) {
		return getResourceForType(locale, ResourceType.CODE_NAME);
	}

	@Override
	public Map<String, String> getMessageResource(Locale locale) {
		return getResourceForType(locale, ResourceType.MESSAGE);
	}

	@Override
	public Map<String, String> getEnumResource(Locale locale) {
		return getResourceForType(locale, ResourceType.ENUM);
	}

	private Map<String, String> getResourceForType(Locale locale, ResourceType type) {
		Map<String, String> result = new HashMap<>();

		Map<ResourceType, Map<String, Map<String, String>>> resource = systemResourceGroupByProgram.get(locale);
		// get default language resource if specify language not found
		if (resource == null) {
			resource = systemResourceGroupByProgram.get(SystemProperties.DEFAULT_LANGUAGE);
		}
		if (resource == null) {
			return Collections.unmodifiableMap(result);
		}

		Collection<Map<String, String>> allPrograms = resource.get(type).values();
		allPrograms.stream().forEach(program -> {
			if (program != null)
				result.putAll(program);
		});

		return Collections.unmodifiableMap(result);

	}

}
