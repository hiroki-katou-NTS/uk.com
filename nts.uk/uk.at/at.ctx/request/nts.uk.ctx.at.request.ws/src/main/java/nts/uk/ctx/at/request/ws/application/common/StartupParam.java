package nts.uk.ctx.at.request.ws.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class StartupParam {
	
	public List<String> empLst;
	
	public List<String> dateLst;
	
	public int appType;
	
	public Integer opHolidayAppType;
	
	public Integer opOvertimeAppAtr;
	
}
