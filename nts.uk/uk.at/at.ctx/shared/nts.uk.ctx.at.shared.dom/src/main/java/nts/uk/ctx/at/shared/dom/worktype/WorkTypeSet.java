package nts.uk.ctx.at.shared.dom.worktype;


import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;


@Getter
public class WorkTypeSet {	
	/*会社ID*/
	private String companyId;

	private WorkTypeCode workTypeCd;
	
	private WorkAtr workAtr;
	
	private Boolean digestPublicHd;
	
	private Boolean holidayAtr;
	
	private Boolean countHodiday;
	
	private CloseAtr closeAtr;
	
	private Integer sumFrame;
	
	private Integer hodidaySet;
	
	private Boolean timeLeaveWork;
	
	private Boolean attendanceTime;
	
	private Boolean genSubHodiday;
	
	private Boolean dayNightTimeAsk;
	
}
