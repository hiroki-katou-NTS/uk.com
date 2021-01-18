package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class SelectWorkOutputDto {
	// 休暇申請起動時の表示情報
	public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	public VacationCheckOutputDto vacationCheckOutputDto;
}
