package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeDateParamMobile {
	
	private String companyId;
	
	private List<String> dates; 
	
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	private ApplyForLeaveDto applyForLeaveDto;
	
	private int appHolidayType;
}
