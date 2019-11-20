package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;

public class AppOverTimeMobDto {
	
	public String applicant;
	
	public String representer;
	
	public String appDate;
	
	public String inputDate;
	
	public Integer appType;
	
	public Integer prePostAtr;
	
	public String workTypeCD;
	
	public String workTypeName;
	
	public String workTimeCD;

	public String workTimeName;
	
	public Integer startTime;
	
	public Integer endTime;
	
	public List<OvertimeFrameDto> frameLst;
	
	public List<OvertimeBreakDto> breakTimeLst;
	
	public List<OvertimeColorCheck> timeLst;
	
	public String appReason;
	
	public String divergenceReasonContent;
	
	public boolean displayCaculationTime;
	
	public boolean displayRestTime;
	
	public boolean appOvertimeNightFlg;
	
	public boolean flexFLag;
	
	public boolean displayBonusTime;
	
	public boolean typicalReasonDisplayFlg;
	
	public boolean displayAppReasonContentFlg;
	
	public boolean displayDivergenceReasonForm;
	
	public boolean displayDivergenceReasonInput;
	
}
