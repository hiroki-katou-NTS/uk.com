package nts.uk.ctx.at.function.dom.adapter.worklocation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordWorkInfoFunAdapterDto {
	private String employeeId;
	
	private String workTimeCode;
	
	private String workTypeCode;

	public RecordWorkInfoFunAdapterDto(String employeeId, String workTimeCode, String workTypeCode) {
		super();
		this.employeeId = employeeId;
		this.workTimeCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}
	
	
}
