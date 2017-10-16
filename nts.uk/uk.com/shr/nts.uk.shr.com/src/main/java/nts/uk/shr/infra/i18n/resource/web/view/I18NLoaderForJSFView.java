package nts.uk.shr.infra.i18n.resource.web.view;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nts.arc.i18n.custom.IInternationalization;

@ApplicationScoped
@Named("i18n")
public class I18NLoaderForJSFView {
	
	@Inject
	IInternationalization internationalization;

	public String getText(String itemId, List<String>params) {
		if (null == itemId || itemId.isEmpty()) {
			return "not found";
		}
		Optional<String> text = internationalization.getItemName(itemId,params.toArray(new String[params.size()]));
		return text.orElse(itemId);
	}
	public String getText(String itemId) {
		if (null == itemId || itemId.isEmpty()) {
			return "not found";
		}
		Optional<String> text = internationalization.getItemName(itemId);
		return text.orElse(itemId);
	}
	
}
