package nts.uk.ctx.at.shared.app.command.scherec.totaltimes.language;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Value
public class InsertTotalTimesLanguageCommand {
	private Integer totalCountNo;
	private String langId;
	private String totalTimesNameEn;

	public TotalTimesLang toDomain(Integer totalCountNo, String langId, String totalTimesNameEn) {
		String companyId = AppContexts.user().companyId();
		return TotalTimesLang.createFromJavaType(companyId, totalCountNo, langId, totalTimesNameEn);
	}
}
