package nts.uk.shr.infra.i18n.loading;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import nts.arc.i18n.custom.ICompanyResourceBundle;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.i18n.custom.ISessionLocale;
import nts.arc.i18n.custom.ISystemResourceBundle;
import nts.arc.i18n.custom.LanguageChangedEvent;
import nts.uk.shr.infra.i18n.SystemProperties;

@SessionScoped
@Stateful
public class MultiLanguageResource implements IInternationalization {
	@Inject
	private ISystemResourceBundle systemResourceBundle;
	@Inject
	private ICompanyResourceBundle companyResourceBundle;
	@Inject
	private ISessionLocale currentLanguage;

	private Map<String, String> codeNameResource;
	private Map<String, String> messageResource;
	private Map<String, String> enumResource;

	private Map<String, String> companyDependCodeNameResource;

	private static final Pattern COMPANYDENPENDITEMPATTERN = Pattern.compile("\\{#(\\w*)\\}");
	private static final Pattern MESSAGEPARAMETERPATTERN = Pattern.compile("\\{([0-9])+(:\\w+)?\\}");

	@Override
	public void currentLanguageChangedEventHandler(@Observes LanguageChangedEvent event) {
		companyResourceBundle.refresh();
		load();
	}
	@PostConstruct
	private void load() {
		// load system resource
		codeNameResource = systemResourceBundle.getCodeNameResource(currentLanguage.getSessionLocale());
		messageResource = systemResourceBundle.getMessageResource(currentLanguage.getSessionLocale());
		enumResource = systemResourceBundle.getEnumResource(currentLanguage.getSessionLocale());

		// load company resource
		companyDependCodeNameResource = companyResourceBundle.getResource();
	}

	@Override
	public Optional<String> getItemName(String id) {

		String text = companyDependCodeNameResource.get(id);
		if (text != null)
			return Optional.of(text);

		text = codeNameResource.get(id);
		if (text == null)
			text = enumResource.get(id);
		return text == null ? Optional.empty() : Optional.of(text);
	}

	@Override
	public Optional<String> getMessage(String messageId, String... params) {
		String message = messageResource.get(messageId);
		if (message == null)
			return Optional.empty();
		message = replaceCompanyDenpendItem(message);
		return Optional.of(replaceMessageParameter(message, Arrays.asList(params)));
	}

	@Override
	public Optional<String> getMessage(String messageId, List<String> params) {
		String message = messageResource.get(messageId);
		if (message == null)
			return Optional.empty();
		message = replaceCompanyDenpendItem(message);
		return Optional.of(replaceMessageParameter(message, params));
	}

	@Override
	public Optional<String> getRawMessage(String messageId) {
		String message = messageResource.get(messageId);
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
	public Map<String, String> getAllMessage() {
		return messageResource;
	}
	@Override
	public Map<String, String> getCodeNameResourceForProgram(String programId) {
		Map<String, String> result = new HashMap<String, String>();

		result.putAll(enumResource);
		result.putAll(systemResourceBundle.getCodeNameOfProgram(currentLanguage.getSessionLocale(), SystemProperties.SYSTEM_ID));
		result.putAll(systemResourceBundle.getCodeNameOfProgram(currentLanguage.getSessionLocale(), programId));
		
		// because company depend resource has higher priority than system
		// resource then we put system before company, if key is duplicated
		// company resource will replace system resource
		// company resource will override system if duplicate
		result.putAll(companyDependCodeNameResource);

		return result;
	}


}
