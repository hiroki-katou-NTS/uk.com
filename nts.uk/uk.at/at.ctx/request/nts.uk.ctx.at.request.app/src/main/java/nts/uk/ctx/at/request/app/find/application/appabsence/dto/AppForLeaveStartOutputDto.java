package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppForLeaveStartOutputDto {
	
	public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	public ApplyForLeaveDto applyForLeaveDto;
	
	
	public static AppForLeaveStartOutputDto fromDomain(AppForLeaveStartOutput output) {
		return new AppForLeaveStartOutputDto(
				AppAbsenceStartInfoDto.fromDomain(output.getAppAbsenceStartInfoOutput()),
				Optional.ofNullable(output.getApplyForLeave()).map(x -> ApplyForLeaveDto.fromDomain(output.getApplyForLeave())).orElse(null));
	}
}
