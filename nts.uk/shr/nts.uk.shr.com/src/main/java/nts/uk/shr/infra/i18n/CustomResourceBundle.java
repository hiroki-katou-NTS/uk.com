package nts.uk.shr.infra.i18n;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nts.arc.i18n.custom.IInternationalization;

@RequestScoped
@Named("i18n")
public class CustomResourceBundle {
	@Inject
	IInternationalization internationalization;

	public String getText(String itemId) {
		if (null == itemId || itemId.isEmpty()) {
			return "not found";
		}
		Optional<String> text = internationalization.getItemName(itemId);
		return text.orElse("not found");
	}
}
