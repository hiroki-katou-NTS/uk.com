package nts.uk.ctx.sys.portal.dom.toppage;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class Language.
 */
@Getter
public class Language extends DomainObject {
	
	/** The lang name. */
	private LanguageName langName;
	
	/** The lang num. */
	private LanguageNumber langNum;
}
