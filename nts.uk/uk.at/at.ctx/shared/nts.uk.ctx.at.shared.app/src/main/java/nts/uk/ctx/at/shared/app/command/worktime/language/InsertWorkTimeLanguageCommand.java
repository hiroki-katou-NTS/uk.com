package nts.uk.ctx.at.shared.app.command.worktime.language;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.language.WorkTimeLanguage;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class InsertWorkTimeLanguageCommand {
	private String workTimeCode;
	private String langId;
	private String name;
	private String abName;

	public WorkTimeLanguage toDomain(String workTimeCode, String langId, String name, String abName) {
		String companyId = AppContexts.user().companyId();
		return WorkTimeLanguage.createFromJavaType(companyId, workTimeCode, langId, name, abName);
	}
}
