package nts.uk.shr.infra.i18n.resource.web.view;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nts.arc.i18n.custom.IInternationalization;
import nts.gul.text.StringUtil;

@ApplicationScoped
@Named("i18n")
public class I18NLoaderForJSFView {
	
	@Inject
	IInternationalization internationalization;
	
	public String getText(String itemId, List<String> params) {
		if (StringUtil.isNullOrEmpty(itemId, true)) {
			return "not found";
		}
		
		return this.internationalization.getItemName(itemId,params.toArray(new String[params.size()]))
				.orElse(itemId);
	}
	
	public String getText(String itemId) {
		return this.getText(itemId, Collections.emptyList());
	}
	
}
