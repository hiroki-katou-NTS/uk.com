package nts.uk.ctx.at.request.app.find.setting.request.application;

import lombok.AllArgsConstructor;
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
}
