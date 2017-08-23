package nts.uk.ctx.at.shared.dom.worktype;

import java.util.UUID;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;


@Getter
public class WorkTypeSet {	
	/*会社ID*/
	private String companyId;

	private WorkTypeCode workTypeCd;
	
	private WorkTypeUnit workAtr;
	
	private Boolean digestPublicHd;
	
	private Boolean holidayAtr;
	
	private Boolean countHodiday;
	
	private CloseAtr closeAtr;
	
	private UUID sumFrame;
	
	private UUID hodidaySet;
	
	private Boolean timeLeaveWork;
	
	private Boolean attendanceTime;
	
	private Boolean genSubHodiday;
	
	private Boolean dayNightTimeAsk;
	
}
