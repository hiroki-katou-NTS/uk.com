package nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicWorkSettingImport {
	private String worktypeCode;
	
	/** The sift code. */
	private String workingCode;
	
	/** The work day division. */
	private int workdayDivision;

	public BasicWorkSettingImport(String worktypeCode, String workingCode, int workdayDivision) {
		super();
		this.worktypeCode = worktypeCode;
		this.workingCode = workingCode;
		this.workdayDivision = workdayDivision;
	}
}


