package nts.uk.ctx.at.record.dom.adapter.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class BasicScheduleSidDto {
	
	public String sId;

	public GeneralDate date;
	
	/** The work type code. */
	public String workTypeCode;

	/** The work time code. */
	public String workTimeCode;

	/** The confirmed atr. */
	public int confirmedAtr;

	/** The working day atr. */
	public int workingDayAtr;

	public BasicScheduleSidDto(String sId, GeneralDate date, String workTypeCode, String workTimeCode, int confirmedAtr,
			int workingDayAtr) {
		super();
		this.sId = sId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
		this.workingDayAtr = workingDayAtr;
	}

}
