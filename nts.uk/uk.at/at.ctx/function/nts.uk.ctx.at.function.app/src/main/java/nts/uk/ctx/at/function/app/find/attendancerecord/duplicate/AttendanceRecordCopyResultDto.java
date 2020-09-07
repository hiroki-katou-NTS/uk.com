package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import java.util.List;

import lombok.Data;

@Data
public class AttendanceRecordCopyResultDto {
	
	/** The data infor return dtos. */
	private List<DataInfoReturnDto> dataInforReturnDtos;
	
	/** The msg err. */
	private List<String> msgErr;
	
	/**
	 * @param dataInforReturnDtos
	 * @param msgErr
	 */
	public AttendanceRecordCopyResultDto(List<DataInfoReturnDto> dataInforReturnDtos, List<String> msgErr) {
		super();
		this.dataInforReturnDtos = dataInforReturnDtos;
		this.msgErr = msgErr;
	}
}
