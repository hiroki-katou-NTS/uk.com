package nts.uk.shr.infra.i18n.loading;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import nts.arc.i18n.custom.ICompanyResourceBundle;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.i18n.custom.ISessionLocale;
import nts.arc.i18n.custom.ISystemResourceBundle;
import nts.arc.i18n.custom.ResourceItem;
import nts.arc.i18n.custom.ResourceType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.SystemProperties;

@Stateful
public class MultiLanguageResource implements IInternationalization {

	private String getCompanyCode() {
		return AppContexts.user().companyCode();
	}

	@Inject
	private ISystemResourceBundle systemResourceBundle;
	@Inject
	private ICompanyResourceBundle companyResourceBundle;
	@Inject
	private ISessionLocale currentLanguage;
	// Map<programId, Map<String, String>>
	private Map<String, Map<String, String>> codeNameResource;
	private Map<String, String> messageResource;

	private Map<String, String> companyCustomizedResource;

	private static final Pattern COMPANYDENPENDITEMPATTERN = Pattern.compile("\\{#(\\w*)\\}");
	private static final Pattern MESSAGEPARAMETERPATTERN = Pattern.compile("\\{([0-9])+(:\\w+)?\\}");

	@PostConstruct
	private void load() {
		loadSystemResource();
		loadCustomizedResource();
	}

	private void loadSystemResource() {
		codeNameResource = systemResourceBundle.getResource(currentLanguage.getSessionLocale(), ResourceType.CODE_NAME);
		if (codeNameResource == null) {
			codeNameResource = systemResourceBundle.getResource(SystemProperties.DEFAULT_LANGUAGE,
					ResourceType.CODE_NAME);
		}

		Map<String, Map<String, String>> tempMessageResource = systemResourceBundle
				.getResource(currentLanguage.getSessionLocale(), ResourceType.MESSAGE);
		if (tempMessageResource == null) {
			tempMessageResource = systemResourceBundle.getResource(SystemProperties.DEFAULT_LANGUAGE,
					ResourceType.MESSAGE);
		}
		messageResource = groupResource(tempMessageResource);
	}

	private Map<String, String> groupResource(Map<String, Map<String, String>> currentLanguageResource) {
		Map<String, String> result = new HashMap<>();
		currentLanguageResource.values().stream().forEach(p -> result.putAll(p));
		return result;

	}

	private void loadCustomizedResource() {
		companyCustomizedResource = companyResourceBundle
				.getResource(getCompanyCode(), currentLanguage.getSessionLocale()).stream()
				.collect(Collectors.toMap(ResourceItem::getCode, ResourceItem::getContent));

	}

	@Override
	public Optional<String> getItemName(String id) {

		String text = companyCustomizedResource.get(id);
		if (text != null)
			return Optional.of(text);

		Map<String, String> allSystemCodeName = groupResource(codeNameResource);
		text = allSystemCodeName.get(id);

		return text == null ? Optional.empty() : Optional.of(text);
	}

	@Override
	public Optional<String> getMessage(String messageId, String... params) {
		return getMessage(messageId, Arrays.asList(params));
	}

	@Override
	public Optional<String> getMessage(String messageId, List<String> params) {
		Optional<String> rawMessage = getRawMessage(messageId);
		if (!rawMessage.isPresent())
			return Optional.empty();
		String result = replaceCompanyDenpendItem(rawMessage.get());
		return Optional.of(replaceMessageParameter(result, params));
	}

	@Override
	public Optional<String> getRawMessage(String messageId) {
		String message = companyCustomizedResource.getOrDefault(messageId, messageResource.get(messageId));
		return message == null ? Optional.empty() : Optional.of(message);
	}

	private String replaceCompanyDenpendItem(String message) {
		Matcher matcher = COMPANYDENPENDITEMPATTERN.matcher(message);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String itemId = matcher.group(1);
			Optional<String> itemName = getItemName(itemId);
			if (!itemName.isPresent())
				continue;
			matcher.appendReplacement(sb, itemName.get());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String replaceMessageParameter(String message, List<String> params) {
		if (params == null || params.size() == 0)
			return message;

		Matcher matcher = MESSAGEPARAMETERPATTERN.matcher(message);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String paramIndexMark = matcher.group(1);
			String format = matcher.group(2);

			if (format != null) {
				// TODO: format param
			}
			try {
				int paramIndex = Integer.parseInt(paramIndexMark);
				if (paramIndex >= params.size())
					continue;
				matcher.appendReplacement(sb, params.get(paramIndex));
			} catch (NumberFormatException ex) {
				Logger.getLogger(this.getClass()).error(ex.getMessage());
				continue;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	@Override
	public Map<ResourceType, Map<String, String>> getResourceOfCompany() {
		Map<ResourceType, Map<String, String>> result = new HashMap<>();
		result.put(ResourceType.MESSAGE, getAllMessage());
		result.put(ResourceType.CODE_NAME, getCodeNameResourceOfProgram(getCompanyCode()));
		return result;
	}

	@Override
	public Map<String, String> getAllMessage() {
		return messageResource;
	}

	private Map<String, String> getCodeNameResourceOfProgram(String programId) {
		Map<String, String> codeName = new HashMap<>();
		codeName.putAll(codeNameResource.getOrDefault(SystemProperties.SYSTEM_ID, new HashMap<>()));
		codeName.putAll(codeNameResource.getOrDefault(programId, new HashMap<>()));
		// company customized will override system default if conflict
		codeName.putAll(companyCustomizedResource);
		return codeName;

	}

	@Override
	public Map<ResourceType, Map<String, String>> getResourceForProgram(String programId) {

		Map<ResourceType, Map<String, String>> result = new HashMap<>();
		result.put(ResourceType.MESSAGE, getAllMessage());
		result.put(ResourceType.CODE_NAME, getCodeNameResourceOfProgram(programId));

		return result;
	}

	@Override
	public Map<ResourceType, Map<String, String>> getSystemDefaultResource() {
		Map<ResourceType, Map<String, String>> result = new HashMap<>();
		result.put(ResourceType.MESSAGE, getAllMessage());
		result.put(ResourceType.CODE_NAME, groupResource(codeNameResource));
		return result;
	}

	@Override
	public Map<String, String> getCustomizeResource() {
		return companyCustomizedResource;
	}
}
