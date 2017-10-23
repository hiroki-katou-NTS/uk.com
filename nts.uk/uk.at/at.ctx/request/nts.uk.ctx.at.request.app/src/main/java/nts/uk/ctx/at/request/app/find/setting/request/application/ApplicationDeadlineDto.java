package nts.uk.ctx.at.request.app.find.setting.request.application;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApplicationDeadlineDto {
	public String companyId;
	public Integer closureId;
	public Integer userAtr;
	public Integer deadline;
	public Integer deadlineCriteria;

	public static ApplicationDeadlineDto convertToDto(ApplicationDeadline domain) {
		return new ApplicationDeadlineDto(domain.getCompanyId(), domain.getClosureId(), domain.getUserAtr().value,
				domain.getDeadline().v(), domain.getDeadlineCriteria().value);
	}
}
