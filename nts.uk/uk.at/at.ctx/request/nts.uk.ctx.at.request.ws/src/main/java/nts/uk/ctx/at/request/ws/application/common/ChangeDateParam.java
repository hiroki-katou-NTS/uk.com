package nts.uk.ctx.at.request.ws.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ChangeDateParam {
	
	private String appDate;
	
	private String startDate;
	
	private String endDate;
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
	private Integer opOvertimeAppAtr;
	
}
