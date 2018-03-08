package nts.uk.ctx.at.record.pub.workinformation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoCheckNotRegisterPubExport {
	private String employeeId;
	
	private String workTimeCode;
	
	private String workTypeCode;

	public InfoCheckNotRegisterPubExport(String employeeId, String workTimeCode, String workTypeCode) {
		super();
		this.employeeId = employeeId;
		this.workTimeCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}
	
}
