package nts.uk.ctx.at.record.dom.workrecord.alarmlist.fourweekfourdayoff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InfoCheckNotRegisterDto {
	private String employeeId;
	
	private String workTimeCode;
	
	private String workTypeCode;

	public InfoCheckNotRegisterDto(String employeeId, String workTimeCode, String workTypeCode) {
		super();
		this.employeeId = employeeId;
		this.workTimeCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}
	
	
}
