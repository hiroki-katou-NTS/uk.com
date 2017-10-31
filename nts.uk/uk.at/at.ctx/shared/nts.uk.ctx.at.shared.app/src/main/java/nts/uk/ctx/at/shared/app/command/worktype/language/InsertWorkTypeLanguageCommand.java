package nts.uk.ctx.at.shared.app.command.worktype.language;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class InsertWorkTypeLanguageCommand {
	private String workTypeCode;
	private String langId;
	private String name;
	private String abName;

	public WorkTypeLanguage toDomain(String workTypeCode, String langId, String name, String abName) {
		String companyId = AppContexts.user().companyId();
		return WorkTypeLanguage.createFromJavaType(companyId, workTypeCode, langId, name, abName);
	}
}
