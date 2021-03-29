package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd_Old;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Data
public class RecordWorkParamHoliday {
	public String employeeID; 
	public String appDate;
	public String siftCD;
	public int prePostAtr;
	public List<CaculationTime> breakTimeHours;
	public String workTypeCD;
	public String appID;
	public AppHdWorkDispInfoCmd_Old appHdWorkDispInfoCmd;
}
