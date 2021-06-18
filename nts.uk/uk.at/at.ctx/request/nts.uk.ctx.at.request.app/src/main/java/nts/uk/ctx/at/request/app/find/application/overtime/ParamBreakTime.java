package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;

@AllArgsConstructor
@NoArgsConstructor
public class ParamBreakTime {
	
	public String companyId;
	
	public String workTypeCode;
	
	public String workTimeCode;
	
	public Integer startTime;
	
	public Integer endTime;
	
	public List<ActualContentDisplayDto> actualContentDisplayDtos;
}
