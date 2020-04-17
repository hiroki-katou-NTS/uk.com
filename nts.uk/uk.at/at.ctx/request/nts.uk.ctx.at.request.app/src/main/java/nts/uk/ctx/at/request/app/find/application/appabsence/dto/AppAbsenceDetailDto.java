package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AppAbsenceDetailDto {
	
	/**
	 * 休暇申請起動時の表示情報
	 */
	public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	/**
	 * 休暇申請
	 */
	public AppAbsenceDto appAbsenceDto;
	
}
